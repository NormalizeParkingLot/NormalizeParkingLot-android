package com.kick.npl.ui.map

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.kick.npl.ui.common.BottomSheet
import com.kick.npl.ui.common.ParkingLotCard
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage


@Preview
@Composable
fun MapPreview() {
    NPLTheme {
        MapScreen(
            generateSampleParkingLots(),
        )
    }
}

@Composable
fun MapScreen(
    parkingLotList: List<ParkingLotData>,
    selectedParkingLot: ParkingLotData? = null,
    filterMap: Map<ParkingLotType, Boolean> = emptyMap(),
    onFilterSelected: (ParkingLotType) -> Unit = {},
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onParkingLotMarkerClicked: (ParkingLotData) -> Unit = {},
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    FilterBar(
        filterEnable = filterMap,
        onFilterSelected = onFilterSelected
    )

    BottomSheet(
        sheetContent = {
            LazyColumn(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(parkingLotList) { parkingLotData ->
                    ParkingLotCard(
                        parkingLotData = parkingLotData,
                        haveBorder = true,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        content = {
            MapScreenContent(
                parkingLotList,
                selectedParkingLot,
                onParkingLotMarkerClicked,
                cameraPositionState
            )
        }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreenContent(
    parkingLotList: List<ParkingLotData>,
    selectedParkingLot: ParkingLotData?,
    onParkingLotMarkerClicked: (ParkingLotData) -> Unit,
    cameraPositionState: CameraPositionState,
) {
    var isFloatingVisible by remember { mutableStateOf(false) }

    Box {
        AnimatedVisibility(
            visible = isFloatingVisible && selectedParkingLot != null,
            enter = slideInVertically { it / 2 } + fadeIn(),
            exit = slideOutVertically { it / 2 } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 85.dp)
        ) {
            ParkingLotCard(
                selectedParkingLot ?: return@AnimatedVisibility,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        NaverMap(
            modifier = Modifier
                .zIndex(-1f)
                .padding(bottom = 56.dp)
                .fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.Navi,
                maxZoom = 18.0,
                minZoom = 5.0,
                isNightModeEnabled = isSystemInDarkTheme()
            ),
            uiSettings = MapUiSettings(
                isTiltGesturesEnabled = false,
                isCompassEnabled = false,
                isZoomControlEnabled = false,
                isRotateGesturesEnabled = false,
                isScaleBarEnabled = false,
                isLocationButtonEnabled = true,
                isLogoClickEnabled = false,
                logoGravity = Gravity.TOP or Gravity.START
            ),
            locationSource = rememberFusedLocationSource(),
            cameraPositionState = cameraPositionState,
            onMapClick = { _, _ -> isFloatingVisible = false }
        ) {
            parkingLotList.forEach { parkingLotData ->
                Marker(
                    icon = OverlayImage.fromResource(R.drawable.ic_marker),
                    iconTintColor = Theme.colors.primary,
                    state = MarkerState(parkingLotData.latLng),
                    captionText = parkingLotData.name,
                    subCaptionText = "${parkingLotData.pricePer10min}원/10분",
                    onClick = {
                        onParkingLotMarkerClicked(parkingLotData)
                        isFloatingVisible = true
                        true
                    }
                )
            }
        }
    }
}

