package com.kick.npl.ui.app

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NPLBottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) = NavigationBar(
    modifier = modifier,
    tonalElevation = 8.dp,

) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NPLBottomRoute.values().forEachIndexed { index, item ->
        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = item.iconId),
                    contentDescription = item.title
                )
            },
            label = { Text(item.title) },
            selected = selected,
            onClick = {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}