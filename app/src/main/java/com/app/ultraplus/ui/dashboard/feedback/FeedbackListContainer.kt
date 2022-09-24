package com.app.ultraplus.ui.dashboard.feedback

import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.navigation.Screen
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

    LaunchedEffect(Unit) {
        viewModel.getFeedbacks(onSuccess = {
            viewModel.feedbacks = it
        }, onFailure = {

        })
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.medium)
            .padding(top = Paddings.medium),
        contentPadding = PaddingValues(bottom = ItemPaddings.xxxLarge.dp)
    ) {

        item {
            Column(Modifier.fillMaxWidth()) {
                Text(text = "Hello $userName", style = AppTheme.typography.regular15, color = AppTheme.colors.TextBlackPrimary)
                Text(text = "Good Afternoon", style = AppTheme.typography.semiBold22, color = AppTheme.colors.TextBlackPrimary)
                Spacer(space = ItemPaddings.xSmall)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = AppTheme.colors.MidBlueSecondary)
                )
                Spacer(space = ItemPaddings.small)
                Text(text = "Feedbacks", style = AppTheme.typography.bold22, color = AppTheme.colors.TextBlackPrimary)
                Spacer(space = ItemPaddings.xxSmall)
            }
        }
        feedbackList(feedbacks = viewModel.feedbacks, onClick = {
            viewModel.selectedFeedback = it
            navHostController.navigate(Screen.FeedbackDetailScreen.route)
        })


    }
}

fun LazyListScope.feedbackList(feedbacks: List<Feedback>, onClick: (Feedback) -> Unit) {
    items(feedbacks, key = { it.id }) {
        FeedbackView(it, onClick)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.FeedbackView(feedback: Feedback, onClick: (Feedback) -> Unit) {
    val statusColor by animateColorAsState(
        targetValue = Feedback.getStatusColor(feedback.status),
        animationSpec = tween(1000, easing = Easing { BounceInterpolator().getInterpolation(it) })
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(animationSpec = tween(800, easing = Easing {
                OvershootInterpolator(4.0f).getInterpolation(it)
            }))
            .height(IntrinsicSize.Max)
            .padding(vertical = Paddings.xSmall)
            .shadow(elevation = 1.dp, shape = AppTheme.shapes.medium)
            .background(color = AppTheme.colors.LightBlueSecondary, shape = AppTheme.shapes.medium)
            .clickable { onClick(feedback) }
            .padding(Paddings.xxSmall)
    ) {
        Spacer(
            modifier = Modifier
                .width(12.dp)
                .fillMaxHeight(1f)
                .clip(shape = AppTheme.shapes.small)
                .background(color = statusColor)
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xSmall, horizontal = Paddings.small)
        ) {
            Text(modifier = Modifier.fillMaxWidth(), text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.bold16.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(feedback.shopName + " ")
                }
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append("- " + feedback.ownerName)
                }
            })
            Spacer(space = 4)
            Text(
                text = feedback.description.replace("<br>", "\n"),
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary,
                maxLines = 6, overflow = TextOverflow.Clip
            )
            Spacer(space = 6)
            Text(modifier = Modifier.fillMaxWidth(), text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append("By ")
                }
                withStyle(style = AppTheme.typography.bold12.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(feedback.userName + " ")
                }
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append("on " + feedback.createdOn.inDisplayFormat())
                }
            })

        }
    }
}