package com.app.ultraplus.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.app.ultraplus.ui.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetStatusBar(statusBarColor: Color = AppTheme.colors.WhitePrimary) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = true
        )
    }
}