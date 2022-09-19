package com.app.ultraplus.ui.dashboard.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.app.ultraplus.ui.dashboard.feedback.FeedbackListContainer
import com.app.ultraplus.ui.dashboard.reimbursement.ReimbursementListContainer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.viewmodel.MainViewModel
import com.google.firebase.annotations.PreviewApi

@Composable
fun MainScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    DashboardScreenPreview(navHostController = navHostController, viewModel = viewModel)
}

@PreviewApi
@Composable
fun DashboardScreenPreview(navHostController: NavHostController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        var currentSelected by remember { mutableStateOf("Feedback") }

        MainContainer(currentSelected)

        BottomBar(currentSelected = currentSelected, onSelectionChanged = {
            currentSelected = it
        }, onAddFeedback = {

        }, onAddReimbursement = {

        })
    }
}

@Composable
fun MainContainer(currentSelected: String) {
    if (currentSelected == "Feedback") FeedbackListContainer() else ReimbursementListContainer()
}