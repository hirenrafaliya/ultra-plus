package com.app.ultraplus.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = AppTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.BluePrimary
        )
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(modifier = Modifier.padding(4.dp),text = text, style = AppTheme.typography.semiBold15, color = AppTheme.colors.WhitePrimary)
        }
    }
}