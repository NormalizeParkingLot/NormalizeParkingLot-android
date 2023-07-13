package com.kick.npl.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kick.npl.ui.map.mapGraph

@Composable
fun NPLNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) = NavHost(
    route = "root",
    navController = navController,
    startDestination = NPLBottomRoute.Map.route,
) {
    mapGraph()
    favoriteGraph()
    testGraph()
    settingGraph()
}

fun NavGraphBuilder.favoriteGraph() {
    composable(NPLBottomRoute.Favorite.route) {
    }
}
fun NavGraphBuilder.testGraph() {
    composable(NPLBottomRoute.Provider.route) {
    }
}
fun NavGraphBuilder.settingGraph() {
    composable(NPLBottomRoute.Setting.route) {
    }
}