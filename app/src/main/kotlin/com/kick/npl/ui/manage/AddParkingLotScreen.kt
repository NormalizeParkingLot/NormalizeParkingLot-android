package com.kick.npl.ui.manage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kick.npl.ui.theme.Theme


@Composable
fun AddParkingLotRoute(
    barcode: String?,
    onClickUp: () -> Unit = {},
) {
    AddParkingLotScreen(
        barcode = barcode,
        isLoading = false,
        onClickUp = onClickUp,
        registerParkingLot = {}
    )
}

@Composable
fun AddParkingLotScreen(
    isLoading: Boolean,
    registerParkingLot: () -> Unit = {},
    onClickUp: () -> Unit = {},
    barcode: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onClickUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = Color.White
                )
            }
            Text(
                text = "주차장 모듈 등록",
                style = Theme.typo.title1B,
                color = Theme.colors.onBackground0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colors.secondaryLine,
            thickness = 1.dp
        )

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
                Column {
                    Text(text = "주차장 정보 입력하기 ")
                    Text(text = "바코드 번호 : $barcode")
                    Text(text = "사진이랑...")
                    Text(text = "부가 정보랑...")
                    Text(text = "가격이랑...")
                    Text(text = "위치랑...")
                    Text(text = "등등...")
                }
            }
        }
    }
}
