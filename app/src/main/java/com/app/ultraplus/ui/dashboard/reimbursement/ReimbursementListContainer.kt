package com.app.ultraplus.ui.dashboard.reimbursement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.ultraplus.ui.theme.AppTheme

@Composable
fun ReimbursementListContainer(){
    Box(modifier = Modifier.fillMaxSize().background(color = AppTheme.colors.StatusYellow))
}