package com.coral.launcher

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.coral.launcher.ui.home.HomeScreen

@Composable
fun CoralApp() {
    MaterialTheme {
        HomeScreen()
    }
}
