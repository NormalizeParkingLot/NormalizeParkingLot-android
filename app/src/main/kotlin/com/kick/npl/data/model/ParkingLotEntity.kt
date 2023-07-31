package com.kick.npl.data.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.naver.maps.geometry.LatLng

data class ParkingLotEntity(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("isBlocked")
    val isBlocked: Boolean = false,
    @PropertyName("isOccupied")
    val isOccupied: Boolean = true,
    @PropertyName("price")
    val price: Int = 0,
    val latlng: GeoPoint = GeoPoint(0.0, 0.0),
)

fun ParkingLotEntity.toParkingLotData(): ParkingLotData {
    return ParkingLotData(
        id = id,
        name = name,
        address = "테스트",
        addressDetail = "테스트",
        imageUri = "https://naver.com",
        latLng = LatLng(37.358315, 127.114454),
        favorite = false,
        pricePer10min = price,
        parkingLotType = ParkingLotType.TYPE_A
    )
}
