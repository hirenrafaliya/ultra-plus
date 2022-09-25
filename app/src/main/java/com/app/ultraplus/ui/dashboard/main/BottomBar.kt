package com.app.ultraplus.ui.dashboard.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.ultraplus.R
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun BoxScope.BottomBar(
    onSelectionChanged: (String) -> Unit,
    currentSelected: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = AppTheme.colors.LightBluePrimary)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp)
            ) {
                BottomBarItem(
                    text = "Feedback",
                    icon = R.drawable.ic_feedback,
                    currentSelected = currentSelected,
                    onClick = {
                        onSelectionChanged("Feedback")
                    })
                BottomBarItem(
                    text = "Reimbursement",
                    icon = R.drawable.ic_reimbursement,
                    currentSelected = currentSelected,
                    onClick = {
                        onSelectionChanged("Reimbursement")
                    })
                BottomBarItem(
                    text = "Profile",
                    icon = R.drawable.ic_profile,
                    currentSelected = currentSelected,
                    onClick = {
                        onSelectionChanged("Profile")
                    })
            }
        }
    }
}

@Composable
fun RowScope.BottomBarItem(text: String, icon: Int, currentSelected: String, onClick: () -> Unit) {
    val isSelected = currentSelected == text
    Column(
        modifier = Modifier
            .weight(1f)
            .height(56.dp)
            .background(color = if (isSelected) AppTheme.colors.LightBluePrimary else AppTheme.colors.LightBlueSecondary)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = if (isSelected) AppTheme.colors.BluePrimary else AppTheme.colors.TextBlackSecondary)
        )
        Spacer(space = 2)
        Text(
            text = text,
            style = AppTheme.typography.semiBold12,
            color = if (isSelected) AppTheme.colors.BluePrimary else AppTheme.colors.TextBlackSecondary
        )
    }
}