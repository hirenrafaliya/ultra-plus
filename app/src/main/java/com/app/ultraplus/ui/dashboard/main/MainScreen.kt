package com.app.ultraplus.ui.dashboard.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.app.ultraplus.ui.dashboard.feedback.FeedbackListContainer
import com.app.ultraplus.ui.dashboard.reimbursement.ReimbursementListContainer
import com.app.ultraplus.ui.navigation.Screen
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
        MainContainer(viewModel.currentSelectedBottomBarItem.value, navHostController, viewModel)

        BottomBar(currentSelected = viewModel.currentSelectedBottomBarItem.value, onSelectionChanged = {
            viewModel.currentSelectedBottomBarItem.value = it
        }, onAddFeedback = {
            navHostController.navigate(Screen.AddFeedbackScreen.route)
        }, onAddReimbursement = {
            navHostController.navigate(Screen.AddReimbursementScreen.route)
        })
    }
}

@Composable
fun MainContainer(currentSelected: String, navHostController: NavHostController, viewModel: MainViewModel) {
    if (currentSelected == "Feedback") FeedbackListContainer(navHostController, viewModel)
    else ReimbursementListContainer(navHostController, viewModel)
}