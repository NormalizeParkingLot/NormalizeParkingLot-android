@file:OptIn(ExperimentalMaterialApi::class)

package com.kick.npl.ui.manage

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.common.ParkingLotCard
import com.kick.npl.ui.theme.Theme

@Composable
fun ManagingScreen(
    navigateToBarcodeScan: () -> Unit,
    navigateToDetail: (ParkingLotData) -> Unit,
    viewModel: ManagingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    LaunchedEffect(viewModel.toast) {
        viewModel.toast.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background)
    ) {
        Text(
            text = "주차장 관리 및 등록",
            style = Theme.typo.title1B,
            color = Theme.colors.onBackground0,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colors.secondaryLine,
            thickness = 1.dp
        )

        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        AnimatedContent(targetState = isLoading, label = "") { loading ->
            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Theme.colors.primary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    if (viewModel.myReservedParkingLots.isNotEmpty()) {
                        item {
                            Text(
                                text = "내가 예약한 주차장",
                                style = Theme.typo.bodyB,
                                color = Theme.colors.onBackground0
                            )
                        }
                        items(viewModel.myReservedParkingLots) { parkingLotData ->
                            Column {
                                ReservedParkingLotCard(
                                    parkingLotData = parkingLotData,
                                    onClick = { navigateToDetail(parkingLotData) },
                                    updateField = { viewModel.setBlock(parkingLotData.id, it) }
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "내가 등록한 주차장",
                            style = Theme.typo.bodyB,
                            color = Theme.colors.onBackground0
                        )
                    }

                    items(viewModel.myRegisteredParkingLots) {
                        ParkingLotCard(
                            parkingLotData = it,
                            onClickCard = { navigateToDetail(it) },
                            enableFavorite = false
                        )
                    }

                    item {
                        ParkingLotManageAddCard(navigateToBarcodeScan)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ParkingLotManageAddCard(
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 4.dp,
        backgroundColor = Theme.colors.surface,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = null,
                tint = Theme.colors.primary,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "주차장 모듈 등록하기",
                style = Theme.typo.body,
                color = Theme.colors.onSurface40,
            )
        }
    }
}

@Composable
fun ReservedParkingLotCard(
    parkingLotData: ParkingLotData,
    onClick: () -> Unit = {},
    updateField: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 4.dp,
        backgroundColor = Theme.colors.surface,
        onClick = onClick,
    ) {
        Column(Modifier.padding(20.dp)) {
            ParkingLotCard(
                parkingLotData = parkingLotData,
                enableFavorite = false
            )
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                NPLButton(
                    buttonStyle = ButtonStyle.Outline,
                    text = "차단기 열기",
                    onClickEnabled = { updateField(false) },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                NPLButton(
                    buttonStyle = ButtonStyle.Outline,
                    text = "차단기 닫기",
                    onClickEnabled = { updateField(true) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}