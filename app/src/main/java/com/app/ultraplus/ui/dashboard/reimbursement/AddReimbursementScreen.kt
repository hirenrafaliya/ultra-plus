package com.app.ultraplus.ui.dashboard.reimbursement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun AddReimbursementScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = AppTheme.colors.StatusRed))
}