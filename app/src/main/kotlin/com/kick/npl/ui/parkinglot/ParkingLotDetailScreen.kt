package com.kick.npl.ui.parkinglot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable
import java.text.DecimalFormat

@Composable
fun ParkingLotDetailScreen(
    parkingLotData: ParkingLotData,
    onClickClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background),
    ) {
        TopBar(onClickClose = onClickClose)
        ParkingLotDetailContent(parkingLotData)
        FloatingButton(23456, true)
    }
}


@Composable
private fun TopBar(onClickClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surface)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_navigate_before_24),
            contentDescription = null,
            tint = Theme.colors.onSurface0,
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .noRippleClickable(onClick = onClickClose)
                .align(Alignment.CenterStart)
        )
        Text(
            text = "주차장 예약하기",
            style = Theme.typo.subheadB,
            color = Theme.colors.onSurface0,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun FloatingButton(
    price: Int,
    isEnabled: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surface)
    ) {
        NPLButton(
            text = "총 ${DecimalFormat("##,###").format(price)} 원 결제하기",
            buttonStyle = ButtonStyle.Filled,
            isEnabled = isEnabled,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ParkingLotDetailContent(
    parkingLotData: ParkingLotData,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        BasicInfoSection(parkingLotData)
        PeriodSection()
        PaymentSection()
        PointSection()
        AgreementSection()
    }
}

@Composable
private fun BasicInfoSection(parkingLotData: ParkingLotData) {
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Row (Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .size(80.dp),
                model = parkingLotData.imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = parkingLotData.name,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = parkingLotData.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = parkingLotData.address,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "주행 요금",
                fontSize = 12.sp,
                color = Theme.colors.onSurface40,
            )
            Text(
                text = buildAnnotatedString {
                    append("10분당 ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    ) {
                        append(android.icu.text.DecimalFormat("#,###").format(parkingLotData.pricePer10min))
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                        )
                    ) { append("원") }
                },
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun PeriodSection() {

}

@Composable
private fun PaymentSection() {

}

@Composable
fun PointSection() {

}

@Composable
fun AgreementSection() {

}

