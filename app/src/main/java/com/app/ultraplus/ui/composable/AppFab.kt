package com.app.ultraplus.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun AppFab(modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(space = 4)
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(shape = AppTheme.shapes.roundShape)
                .clickable(onClick = onClick)
                .background(
                    color = AppTheme.colors.LightBluePrimary,
                    shape = AppTheme.shapes.roundShape
                )
                .padding(2.dp)
                .background(
                    color = AppTheme.colors.MidBlueSecondary,
                    shape = AppTheme.shapes.roundShape
                )
                .padding(2.dp)
                .background(
                    color = AppTheme.colors.BluePrimary,
                    shape = AppTheme.shapes.roundShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Rounded.Add,
                tint = AppTheme.colors.WhitePrimary,
                contentDescription = ""
            )
        }
    }
}