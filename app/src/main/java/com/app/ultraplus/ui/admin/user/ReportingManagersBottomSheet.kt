package com.app.ultraplus.ui.admin.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.app.ultraplus.network.model.User
import com.app.ultraplus.ui.composable.DialogWindow
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.Paddings

@Composable
fun ReportingManagerDialog(
    managers: List<User>,
    currentSelected: User?,
    onClosed: () -> Unit,
    onReportingManagerSelected: (User) -> Unit
) {
    DialogWindow(title = "Select Reporting Manager") {
        Column {
            managers.forEach {
                SelectableReportingManagerView(
                    user = it,
                    onSelect = onReportingManagerSelected,
                    isSelected = it.id == currentSelected?.id
                )
            }
        }
    }
}

@Composable
fun SelectableReportingManagerView(user: User, onSelect: (User) -> Unit, isSelected: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(Paddings.xSmall)
            .shadow(elevation = 1.dp, shape = AppTheme.shapes.medium)
            .background(color = AppTheme.colors.LightBlueSecondary, shape = AppTheme.shapes.medium)
            .clickable { onSelect(user) }
            .padding(Paddings.small)) {

        Text(
            text = user.userName,
            style = AppTheme.typography.semiBold15,
            color = if (isSelected) AppTheme.colors.BluePrimary else AppTheme.colors.TextBlackPrimary
        )
    }
}