package com.kick.npl.data.repository

import com.kick.npl.data.model.ParkingLotEntity
import com.kick.npl.model.ParkingLotData

interface ParkingLotsRepository {
    suspend fun setParkingLot(parkingLot: ParkingLotEntity)
    suspend fun getParkingLot(id: String): ParkingLotEntity?
    suspend fun getAllParkingLots(): List<ParkingLotData>?
}