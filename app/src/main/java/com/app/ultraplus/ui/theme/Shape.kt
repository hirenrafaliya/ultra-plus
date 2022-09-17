package com.app.ultraplus.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AppShape {
    val small = RoundedCornerShape(2.dp)
    val medium = RoundedCornerShape(4.dp)
    val large = RoundedCornerShape(8.dp)

    val roundShape = RoundedCornerShape(50)
}

internal val LocalShapes = staticCompositionLocalOf { AppShape() }

object Paddings {
    val small: Dp = 8.dp
    val medium: Dp = 12.dp
    val large: Dp = 18.dp
}

object ItemPaddings {
    val xxSmall: Dp = 8.dp
    val xSmall: Dp = 12.dp
    val small: Dp = 18.dp
    val medium: Dp = 24.dp
    val large: Dp = 32.dp
    val xLarge: Dp = 46.dp
}