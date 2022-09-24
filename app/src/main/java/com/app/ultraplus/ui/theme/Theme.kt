package com.app.ultraplus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: AppShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val themeColor = if (isSystemInDarkTheme()) LightAppColors() else LightAppColors()

    MaterialTheme(
        colorScheme = AppTheme.colors.materialColors
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides AppRippleTheme,
            LocalColors provides themeColor,
            LocalTypography provides AppTheme.typography,
            LocalShapes provides AppTheme.shapes
        ) { content() }
    }
}

private object AppRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = AppTheme.colors.BluePrimary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        AppTheme.colors.BluePrimary,
        lightTheme = !isSystemInDarkTheme()
    )

}