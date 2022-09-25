package com.app.ultraplus.ui.dashboard.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.UserType
import com.app.ultraplus.ui.dashboard.feedback.FeedbackListContainer
import com.app.ultraplus.ui.dashboard.profile.ProfileScreen
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
    val userType by remember { mutableStateOf(UserPref.getUser().userType) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        MainContainer(viewModel.currentSelectedBottomBarItem.value, navHostController, viewModel)

        BottomBar(currentSelected = viewModel.currentSelectedBottomBarItem.value,
            onSelectionChanged = {
                viewModel.currentSelectedBottomBarItem.value = it
            })
    }
}

@Composable
fun MainContainer(
    currentSelected: String,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    AnimatedVisibility(
        visible = currentSelected == "Feedback",
        enter = slideIn(
            animationSpec = tween(800),
            initialOffset = { IntOffset(-it.width, 0) }) + fadeIn(
            animationSpec = tween(
                800
            )
        ),
        exit = slideOut(
            animationSpec = tween(800),
            targetOffset = { IntOffset(-it.width, 0) }) + fadeOut(
            animationSpec = tween(
                800
            )
        )
    ) {
        FeedbackListContainer(modifier = Modifier, navHostController, viewModel)
    }
    AnimatedVisibility(
        visible = currentSelected == "Reimbursement",
        enter = slideIn(
            animationSpec = tween(800),
            initialOffset = { IntOffset(it.width, 0) }) + fadeIn(animationSpec = tween(800)),
        exit = slideOut(
            animationSpec = tween(800),
            targetOffset = { IntOffset(it.width, 0) }) + fadeOut(animationSpec = tween(800))
    ) {
        ReimbursementListContainer(modifier = Modifier, navHostController, viewModel)
    }
    AnimatedVisibility(
        visible = currentSelected == "Profile",
        enter = slideIn(
            animationSpec = tween(800),
            initialOffset = { IntOffset(it.width, 0) }) + fadeIn(animationSpec = tween(800)),
        exit = slideOut(
            animationSpec = tween(800),
            targetOffset = { IntOffset(it.width, 0) }) + fadeOut(animationSpec = tween(800))
    ) {
        ProfileScreen(modifier = Modifier, navHostController, viewModel)
    }
}