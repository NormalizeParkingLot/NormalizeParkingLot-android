package com.kick.npl.ui.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
    modifier = modifier,
    navController = navController,
    startDestination = NPLBottomRoute.Map.route,
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
) {
    mapGraph(navController)
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