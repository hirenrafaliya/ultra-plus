package com.app.ultraplus.ui.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/* Declare light app colors */
abstract class AppColors {

    open val BluePrimary = Color(0xFF147EFB)
    open val BlueSecondary = Color(0xFF147EFB)

    open val LightBluePrimary = Color(0xFFE2EFFF)
    open val LightBlueSecondary = Color(0xFFF8FBFF)

    open val BlackPrimary = Color(0xFF000000)
    open val BlackSecondary = Color(0xFF000000)

    open val TextBlackPrimary = Color(0xFF2F2F2F)
    open val TextBlackSecondary = Color(0xFF626262)

    open val StatusRed = Color(0xFFFF6240)
    open val StatusYellow = Color(0xFFFFE040)
    open val StatusGreen = Color(0xFF94FF40)
}

class LightAppColors : AppColors()

val LocalColors: ProvidableCompositionLocal<AppColors> = staticCompositionLocalOf { LightAppColors() }
