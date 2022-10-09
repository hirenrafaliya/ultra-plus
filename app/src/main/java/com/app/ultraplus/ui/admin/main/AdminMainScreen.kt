package com.app.ultraplus.ui.admin.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.app.ultraplus.ui.admin.feedback.FeedbackAdminScreen
import com.app.ultraplus.ui.admin.user.UserAdminScreen
import com.app.ultraplus.ui.dashboard.feedback.FeedbackListContainer
import com.app.ultraplus.ui.dashboard.main.BottomBar
import com.app.ultraplus.ui.dashboard.main.MainContainer
import com.app.ultraplus.ui.dashboard.profile.ProfileScreen
import com.app.ultraplus.ui.dashboard.reimbursement.ReimbursementListContainer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun AdminMainScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        AdminMainContainer(
            viewModel.currentSelectedBottomBarItem.value,
            navHostController,
            viewModel
        )

        AdminBottomBar(currentSelected = viewModel.currentSelectedBottomBarItem.value,
            onSelectionChanged = {
                viewModel.currentSelectedBottomBarItem.value = it
            })
    }

}

@Composable
fun AdminMainContainer(
    currentSelected: String,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    fun getFadeIn() = fadeIn(animationSpec = tween(800))
    fun getFadeOut() = fadeOut(animationSpec = tween(800))

    AnimatedVisibility(
        visible = currentSelected == "Feedback",
        enter = getFadeIn(),
        exit = getFadeOut()
    ) {
        FeedbackAdminScreen(navHostController = navHostController, viewModel = viewModel)
    }
    AnimatedVisibility(
        visible = currentSelected == "Reimbursement",
        enter = getFadeIn(),
        exit = getFadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        )
    }
    AnimatedVisibility(
        visible = currentSelected == "Manager",
        enter = getFadeIn(),
        exit = getFadeOut()
    ) {
        UserAdminScreen(navHostController, viewModel)
    }
}