package com.app.ultraplus.ui.dashboard.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun FeedbackListContainer(navHostController: NavHostController, viewModel: MainViewModel) {

    FeedbackListContainerPreview(navHostController = navHostController, viewModel = viewModel)
}

@Composable
fun FeedbackListContainerPreview(navHostController: NavHostController, viewModel: MainViewModel) {

    val userName by remember { mutableStateOf(UserPref.getUser().userName) }
    var feedbacks by remember { mutableStateOf(listOf<Feedback>()) }

    LaunchedEffect(Unit) {
        viewModel.getFeedbacks(onSuccess = {
            feedbacks = it
        }, onFailure = {

        })
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.medium)
            .padding(top = Paddings.medium)
    ) {

        item {
            Column(Modifier.fillMaxWidth()) {
                Text(text = "Hello $userName", style = AppTheme.typography.regular15, color = AppTheme.colors.TextBlackPrimary)
                Text(text = "Good Afternoon", style = AppTheme.typography.semiBold22, color = AppTheme.colors.TextBlackPrimary)
                Spacer(space = ItemPaddings.small)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = AppTheme.colors.LightBlueSecondary)
                )
                Spacer(space = ItemPaddings.medium)
                Text(text = "Feedbacks", style = AppTheme.typography.bold22, color = AppTheme.colors.TextBlackPrimary)
                Spacer(space = ItemPaddings.large)
            }
        }
        feedbackList(feedbacks = feedbacks)


    }
}

fun LazyListScope.feedbackList(feedbacks: List<Feedback>) {
    items(feedbacks) {
        FeedbackView(it)
    }
}

@Composable
fun FeedbackView(feedback: Feedback) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Paddings.xxSmall)
    ) {
        Spacer(
            modifier = Modifier
                .width(4.dp)
                .height(32.dp)
                .background(color = feedback.getStatusColor())
        )

        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text(modifier = Modifier.fillMaxWidth(), text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.semiBold15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(feedback.shopName + "")
                }
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append(feedback.ownerName)
                }
            })
            Text(
                text = feedback.description.replace("<br>", "\n"),
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary,
                maxLines = 6, overflow = TextOverflow.Clip
            )
            Text(modifier = Modifier.fillMaxWidth(), text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append("By ")
                }
                withStyle(style = AppTheme.typography.semiBold12.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(feedback.userName + " ")
                }
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append(feedback.createdOn.inDisplayFormat())
                }
            })

        }
    }
}