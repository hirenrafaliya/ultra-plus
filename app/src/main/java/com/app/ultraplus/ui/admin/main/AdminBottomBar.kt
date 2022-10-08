package com.app.ultraplus.ui.admin.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.app.ultraplus.R
import com.app.ultraplus.ui.dashboard.main.BottomBarItem
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun BoxScope.AdminBottomBar(
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
                    text = "Manager",
                    icon = R.drawable.ic_profile,
                    currentSelected = currentSelected,
                    onClick = {
                        onSelectionChanged("Manager")
                    })
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
            }
        }
    }
}