package com.kick.npl.ui.map

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kick.npl.data.repository.MapsRepository
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.kick.npl.ui.map.model.SelectedParkingLotData
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val parkingLotsRepository: ParkingLotsRepository,
) : ViewModel() {

    private var _parkingLots: MutableStateFlow<List<ParkingLotData>> = MutableStateFlow(emptyList())
    val parkingLots: StateFlow<List<ParkingLotData>> = _parkingLots.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentLatLng: LatLng? = null

    val cameraPositionState = CameraPositionState()

    val filterMap = mutableStateMapOf<ParkingLotType, Boolean>()

    var selectedParkingLot by mutableStateOf<SelectedParkingLotData?>(null)
        private set

    init {
        ParkingLotType.values().forEach { filterMap[it] = false }
        // Mock
        //_parkingLots.value = generateSampleParkingLots()
        viewModelScope.launch {
            _parkingLots.value = parkingLotsRepository.getAllParkingLots() ?: emptyList()
        }
    }

    fun onLocationChange(location: Location) {
        currentLatLng = LatLng(location.latitude, location.longitude)
    }

    @OptIn(ExperimentalNaverMapApi::class)
    fun onMarkerClicked(
        parkingLotData: ParkingLotData,
    ) = viewModelScope.launch(Dispatchers.IO) {

        val route = getDrivingRoute(
            currentLatLng ?: return@launch,
            parkingLotData.latLng,
        ) ?: return@launch

        val (leftBottom, rightTop) = route.summary.bbox.let {
            LatLng(it[0][1], it[0][0]) to LatLng(it[1][1], it[1][0])
        }

        cameraPositionState.animate(
            update = CameraUpdate.fitBounds(
                LatLngBounds(leftBottom, rightTop), 200
            )
        )

        selectedParkingLot = SelectedParkingLotData(
            parkingLotData = parkingLotData,
            routeFromCurrent = route,
        )
    }

    private suspend fun getDrivingRoute(
        start: LatLng,
        goal: LatLng,
        waypoints: List<LatLng> = emptyList(),
    ) = withContext(viewModelScope.coroutineContext) {
        mapsRepository
            .getDrivingRoute(start, goal, waypoints)
            .onFailure {
                _eventFlow.emit(UiEvent.Error("문제가 발생했습니다."))
                Log.e("OrderDetailViewModel", it.message ?: "")
                it.printStackTrace()
            }
            .getOrNull()
    }

    fun onFilterSelected(filterType: ParkingLotType) {
        filterMap[filterType] = !(filterMap[filterType] ?: false)
    }

    sealed class UiEvent {
        data class Error(val message: String) : UiEvent()
        object FullSizeMap : UiEvent()
    }
}