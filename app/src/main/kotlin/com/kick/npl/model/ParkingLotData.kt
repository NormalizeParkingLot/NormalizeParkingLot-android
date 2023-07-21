package com.kick.npl.model

import com.naver.maps.geometry.LatLng

data class ParkingLotData(
    val id: Int,
    val name: String,
    val address: String,
    val addressDetail: String,
    val imageUri: String,
    val latLng: LatLng,
    val favorite: Boolean,
    val pricePer10min: Int,
    val parkingLotType: ParkingLotType,
) {
    companion object {
        val TEST = ParkingLotData(
            id = 0,
            name = "테스트 주차장",
            address = "테스트 주소",
            addressDetail = "테스트 상세 주소",
            imageUri = "테스트 uri",
            latLng = LatLng(0.0, 0.0),
            favorite = false,
            pricePer10min = 1000,
            parkingLotType = ParkingLotType.TYPE_A,
        )
    }
}
