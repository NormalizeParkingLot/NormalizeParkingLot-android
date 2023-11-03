package com.kick.npl.ui.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import com.kick.npl.data.local.UserIdDataSource
import com.kick.npl.data.model.toParkingLotData
import com.kick.npl.data.repository.MapsRepository
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddParkingLotViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val parkingLotsRepository: ParkingLotsRepository,
    private val dataSource: UserIdDataSource,
) : ViewModel() {

    private val userId by lazy { dataSource.getUserId() }

    val parkingLotData = mutableStateOf(AddParkingLotUiState())
    val temporalAddressName = mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)
        private set

    private val _result = MutableSharedFlow<AddParkingLotResult>()
    val result = _result.asSharedFlow()

    fun getAddressName(latlng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            mapsRepository.getMinifiedAddress(latlng).onSuccess {
                temporalAddressName.value = it
            }.onFailure {
                _result.emit(AddParkingLotResult.Failure("주소를 불러오는데 실패했습니다."))
            }
        }
    }

    fun registerParkingLot() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                parkingLotsRepository.setParkingLotData(
                    parkingLotData.value.toParkingLotData(),
                    it
                )
            }

            _result.emit(AddParkingLotResult.Success)
        }
        isLoading = false
    }

    fun getData(id: String) {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {

            val data = parkingLotsRepository.getParkingLot(id)?.toParkingLotData(id)

            if (data != null) {
                parkingLotData.value = parkingLotData.value.copy(
                    id = id,
                    title = data.name,
                    address = data.address,
                    description = data.addressDetail,
                    latlng = data.latLng.let { GeoPoint(it.latitude, it.longitude) },
                    pricePer10min = data.pricePer10min,
                )
            } else {
                parkingLotData.value = parkingLotData.value.copy(id = id)
            }
        }
        isLoading = false
    }
}

sealed class AddParkingLotResult {
    data object Success : AddParkingLotResult()
    data class Failure(val message: String) : AddParkingLotResult()

    data object Update : AddParkingLotResult()
}

data class AddParkingLotUiState(
    val id: String? = null,
    val title: String? = null,
    val address: String? = null,
    val description: String? = null,
    val latlng: GeoPoint? = null,
    val pricePer10min: Int? = null,
    val imageUrl: String? = null,
) {
    fun toParkingLotData() = ParkingLotData(
        id = id ?: "",
        name = title ?: "",
        address = address ?: "",
        addressDetail = description ?: "",
        imageUri = "https://i.imgur.com/nVutgKq.png",
        latLng = LatLng(latlng?.latitude ?: 0.0, latlng?.longitude ?: 0.0),
        favorite = false,
        pricePer10min = pricePer10min ?: 0,
        parkingLotType = com.kick.npl.model.ParkingLotType.TYPE_A,
    )
}
