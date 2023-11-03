package com.kick.npl.ui.map

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.repository.MapsRepository
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.kick.npl.ui.map.model.ParkingDateTime
import com.kick.npl.ui.map.model.SelectedParkingLotData
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val parkingLotsRepository: ParkingLotsRepository,
) : ViewModel() {

    private var parkingLots = mutableMapOf<String, ParkingLotData>()
    val parkingLotList: List<ParkingLotData> get() = parkingLots.values.toList()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentLatLng: LatLng? = null

    val cameraPositionState = CameraPositionState()

    var parkingDateTime by mutableStateOf(
        ParkingDateTime(LocalDateTime.now(), LocalDateTime.now().plusHours(2))
    )
        private set

    var selectedParkingLot by mutableStateOf<SelectedParkingLotData?>(null)
        private set

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllParkingLots()
        selectedParkingLot = null
    }

    fun getAllParkingLots() = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.emit(true)
        val result = parkingLotsRepository
            .getAllParkingLots()
            ?.asSequence()
            ?.filter { it.provider.isNotBlank() }
            ?.map { it.id to it }
            ?.toMap()
            ?.toMutableMap()

        if(result != null) parkingLots = result
        _isLoading.emit(false)
    }

    fun onParkingDateTimeChanged(parkingDateTime: ParkingDateTime) {
        this.parkingDateTime = parkingDateTime
    }

    fun onLocationChange(location: Location) {
        currentLatLng = LatLng(location.latitude, location.longitude)
    }

    fun onMarkerUnselected() {
        selectedParkingLot = null
    }

    @OptIn(ExperimentalNaverMapApi::class)
    fun onMarkerClicked(
        parkingLotId: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.emit(true)
        val parkingLotData = parkingLots[parkingLotId] ?: return@launch

        selectedParkingLot = SelectedParkingLotData(
            parkingLotData = parkingLotData,
            routeFromCurrent = null,
        )

        val route = getDrivingRoute(
            currentLatLng ?: return@launch,
            parkingLotData.latLng,
        ) ?: return@launch

        selectedParkingLot = selectedParkingLot?.copy(routeFromCurrent = route)

        val (leftBottom, rightTop) = route.summary.getBounds()
        cameraPositionState.animate(
            update = CameraUpdate.fitBounds(
                LatLngBounds(leftBottom, rightTop), 200, 300, 200, 500
            )
        )
        _isLoading.emit(false)
    }

    private suspend fun getDrivingRoute(
        start: LatLng,
        goal: LatLng,
        waypoints: List<LatLng> = emptyList(),
    ) = withContext(viewModelScope.coroutineContext) {
        mapsRepository.getDrivingRoute(start, goal, waypoints).onFailure {
            _eventFlow.emit(UiEvent.Error("문제가 발생했습니다."))
            Log.e("OrderDetailViewModel", it.message ?: "")
            it.printStackTrace()
        }.getOrNull()
    }


    fun onClickFavorite(parkingLotId: String) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.emit(true)
        val parkingLotData = parkingLots[parkingLotId] ?: return@launch

        parkingLotsRepository.toggleFavorite(
            id = parkingLotId, favorite = !parkingLotData.favorite
        )

        parkingLots = parkingLots.apply {
            this[parkingLotId] = parkingLotData.copy(favorite = !parkingLotData.favorite)
        }

        if (selectedParkingLot?.parkingLotData?.id == parkingLotId) {
            selectedParkingLot = selectedParkingLot?.copy(
                parkingLotData = parkingLots[parkingLotId] ?: return@launch
            )
        }
        _isLoading.emit(false)
    }

    fun onClickParkingLotCard(parkingLotId: String) {
        viewModelScope.launch {
            val parkingLotData = parkingLots[parkingLotId] ?: return@launch
            _eventFlow.emit(UiEvent.NavigateToDetail(parkingLotData, parkingDateTime))
        }
    }

    sealed class UiEvent {
        data class Error(val message: String) : UiEvent()
        data class NavigateToDetail(
            val parkingLotData: ParkingLotData,
            val parkingDateTime: ParkingDateTime
        ) : UiEvent()
        data object FullSizeMap : UiEvent()
    }
}