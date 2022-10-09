package com.app.ultraplus.ui.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ultraplus.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogWindow(
    title: String,
    maxHeight: Dp = 300.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.background(
            color = AppTheme.colors.WhitePrimary,
            shape = AppTheme.shapes.medium
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = title,
                color = AppTheme.colors.TextBlackSecondary,
                style = AppTheme.typography.regular12,
                textAlign = TextAlign.Start
            )
        }
        Column(
            Modifier
                .heightIn(max = maxHeight)
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}