package com.kick.npl.ui.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

) : ViewModel() {

    private var _parkingLots: MutableStateFlow<List<ParkingLotData>> = MutableStateFlow(emptyList())
    val parkingLots: StateFlow<List<ParkingLotData>> = _parkingLots.asStateFlow()

    val cameraPositionState = CameraPositionState()

    val filterMap = mutableStateMapOf<ParkingLotType, Boolean>()

    var selectedParkingLot by mutableStateOf<ParkingLotData?>(null)
        private set

    init {
        ParkingLotType.values().forEach { filterMap[it] = false }
        _parkingLots.value = generateSampleParkingLots()
        val bounds = LatLngBounds.from(_parkingLots.value.map { it.latLng })
        cameraPositionState.position = CameraPosition(bounds.center, 11.0)
    }

    @OptIn(ExperimentalNaverMapApi::class)
    fun onMarkerClicked(parkingLotData: ParkingLotData) {
        viewModelScope.launch {
            cameraPositionState.animate(
                update = CameraUpdate.scrollTo(parkingLotData.latLng)
            )
        }
        selectedParkingLot = parkingLotData
    }

    fun onFilterSelected(filterType: ParkingLotType) {
        filterMap[filterType] = !(filterMap[filterType] ?: false)
    }
}

fun generateSampleParkingLots(): List<ParkingLotData> = buildList {
    ParkingLotData(
        id = 7417,
        name = "분당구청 주차장",
        address = "경기도 성남시 분당구 정자동 206",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.358315, 127.114454),
        favorite = false,
        pricePer10min = 500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = 7417,
        name = "수지구청 주차장",
        address = "경기도 성남시 수정구 태평동 3377",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.443947, 127.137157),
        favorite = true,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_B
    ).let { add(it) }

    ParkingLotData(
        id = 7417,
        name = "중원구청 주차장",
        address = "경기도 성남시 중원구 상대원동 3971",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.437705, 127.154780),
        favorite = true,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_C
    ).let { add(it) }

    ParkingLotData(
        id = 7417,
        name = "성남시청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.419662, 127.126939),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }
}
