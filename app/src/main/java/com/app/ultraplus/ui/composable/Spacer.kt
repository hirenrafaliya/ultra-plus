package com.app.ultraplus.ui.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.Spacer(space: Int) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(space.div(2).dp))
}

@Composable
fun RowScope.Spacer(space: Int) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(space.div(2).dp))
}
