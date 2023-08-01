package com.kick.npl.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kick.npl.ui.app.NPLBottomRoute

const val MAP_SCREEN = "mapScreen"

fun NavGraphBuilder.mapGraph(
    navController: NavController,
) {
    navigation(
        startDestination = MAP_SCREEN,
        route = NPLBottomRoute.Map.route
    ) {
        composable(
            route = MAP_SCREEN,
        ) {
            MapRoute()
        }
    }
}

@Composable
fun MapRoute(
    viewModel: MapViewModel = hiltViewModel(),
) {
    MapScreen(
        parkingLotList = viewModel.parkingLotList,
        selectedParkingLot = viewModel.selectedParkingLot,
        cameraPositionState = viewModel.cameraPositionState,
        parkingDateTime = viewModel.parkingDateTime,
        onParkingLotMarkerClicked = viewModel::onMarkerClicked,
        onMarkerUnselected = viewModel::onMarkerUnselected,
        onLocationChange = viewModel::onLocationChange,
        onParkingDateTimeChanged = viewModel::onParkingDateTimeChanged,
        onClickFavorite = viewModel::onClickFavorite,
        onClickParkingLotCard = viewModel::onClickParkingLotCard,
    )
}
