package com.app.ultraplus.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings

@Composable
fun AppBack(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .height(40.dp)
            .clickable(onClick = onClick, interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple(bounded = false))
            .padding(vertical = ItemPaddings.xxSmall.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = AppTheme.colors.TextBlackPrimary)
        Spacer(space = ItemPaddings.xxSmall)
        Text(text = "Back", style = AppTheme.typography.semiBold15, color = AppTheme.colors.TextBlackPrimary)
    }
}