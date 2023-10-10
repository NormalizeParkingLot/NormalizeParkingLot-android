package com.kick.npl.ui.parkinglot

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kick.npl.model.ParkingLotData

const val PARKING_LOT_DETAIL = "parkingLotDetail"
const val PARKING_LOT_DETAIL_KEY = "parkingLotDetailKey"

fun NavGraphBuilder.parkingLotGraph(
    navController: NavController,
) {
    composable(
        route = PARKING_LOT_DETAIL,
    ) {
        val parkingLotData = it.savedStateHandle.get<ParkingLotData>(PARKING_LOT_DETAIL_KEY)
        ParkingLotDetailScreen(
            parkingLotData = parkingLotData!!,
            onClickClose = { navController.popBackStack() }
        )
    }
}

fun NavController.navigateToParkingLotDetail(parkingLotData: ParkingLotData) {
    navigate(PARKING_LOT_DETAIL)
    currentBackStackEntry?.savedStateHandle?.set(PARKING_LOT_DETAIL_KEY, parkingLotData)
}