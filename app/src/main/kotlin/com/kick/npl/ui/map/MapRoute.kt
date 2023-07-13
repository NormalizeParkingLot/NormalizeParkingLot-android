package com.kick.npl.ui.map

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kick.npl.ui.app.NPLBottomRoute

fun NavGraphBuilder.mapGraph() {
    composable(NPLBottomRoute.Map.route) {
        MapRoute()
    }
}

@Composable
fun MapRoute() {
    MapScreen()
}
