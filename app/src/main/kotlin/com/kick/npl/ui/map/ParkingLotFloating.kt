package com.kick.npl.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kick.npl.model.ParkingLotData

@Composable
fun ParkingLotFloating(
    parkingLotData: ParkingLotData,
    onClick: () -> Unit = {},
) = Surface(
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(1.dp, Color.Gray),
    contentColor = MaterialTheme.colors.onPrimary,
    modifier = Modifier.clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "${parkingLotData.name}\n${parkingLotData.address}\n${parkingLotData.addressDetail}\n${parkingLotData.pricePer10min}")
    }
}