package com.app.ultraplus.ui.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, isLoading: Boolean = false, onClick: () -> Unit) {
    val indicatorHeight by animateFloatAsState(targetValue = if (isLoading) 10f else 0f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = AppTheme.shapes.medium)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            shape = AppTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.BluePrimary
            )
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = text,
                    style = AppTheme.typography.semiBold15,
                    color = AppTheme.colors.WhitePrimary
                )
            }
        }
        LinearProgressIndicator(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(indicatorHeight.dp),
            color = AppTheme.colors.StatusRed.copy(alpha = 0.8f),
            trackColor = AppTheme.colors.StatusRed.copy(alpha = 0.15f)
        )
    }
}