package com.app.ultraplus.ui.admin.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.dashboard.feedback.feedbackList
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun FeedbackAdminScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val user by remember { mutableStateOf(UserPref.getUser()) }

    LaunchedEffect(Unit) {
        viewModel.getFeedbacks(onSuccess = {
            viewModel.feedbacks = it
        }, onFailure = {

        })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.medium)
                .padding(top = Paddings.medium),
            contentPadding = PaddingValues(bottom = ItemPaddings.xxxLarge.dp),
        ) {
            item {
                Column {
                    Spacer(space = ItemPaddings.small)
                    Text(
                        text = "All Feedbacks",
                        style = AppTheme.typography.bold22,
                        color = AppTheme.colors.TextBlackPrimary
                    )
                    Spacer(space = ItemPaddings.xxSmall)
                }
            }

            feedbackList(viewModel.feedbacks, {

            })
        }
    }
}