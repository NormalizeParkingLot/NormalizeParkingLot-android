package com.kick.npl.model

import com.google.firebase.firestore.GeoPoint
import com.kick.npl.data.model.ParkingLotEntity
import com.naver.maps.geometry.LatLng

data class ParkingLotData(
    val id: String,
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
            id = "",
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

fun ParkingLotData.toParkingLotEntity() : ParkingLotEntity {
    return ParkingLotEntity(
        name = name,
        isBlocked = false,
        isOccupied = false,
        price = pricePer10min,
        imageUri = imageUri,
        latlng = GeoPoint(latLng.latitude, latLng.longitude),
    )
}
