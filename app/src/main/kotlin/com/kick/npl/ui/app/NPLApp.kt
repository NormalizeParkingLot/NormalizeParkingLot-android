package com.kick.npl.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kick.npl.ui.theme.NPLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NPLApp(
    appState: NPLAppState = rememberNPLAppState(),
) {
    NPLTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NPLBottomNavBar(navController = appState.navController)
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { contentPadding ->
            NPLNavHost(
                navController = appState.navController,
                modifier = Modifier.padding(contentPadding),
            )
        }
    }
}

@Preview
@Composable
fun NPLAppPreview() {
    NPLTheme {
        NPLApp()
    }
}
