package com.kick.npl.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kick.npl.ui.theme.Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    peekHeight: Dp = 56.dp,
    onExpanded: () -> Unit = {},
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            confirmValueChange = {
                when (it) {
                    SheetValue.Expanded -> onExpanded()
                    else -> Unit
                }
                true
            }
        ),
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShadowElevation = 8.dp,
        sheetShape = RectangleShape,
        sheetPeekHeight = peekHeight,
        sheetContainerColor = Theme.colors.background,
        sheetContentColor = Theme.colors.onBackground0,
        sheetContent = sheetContent,
    ) {
        content()
    }
}