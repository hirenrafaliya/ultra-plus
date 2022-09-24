package com.app.ultraplus.ui.feedback

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.R
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.FeedbackStatus
import com.app.ultraplus.ui.composable.AppBack
import com.app.ultraplus.ui.composable.AppTextField
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel
import java.util.*

@Composable
fun FeedbackDetailScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    FeedbackDetailScreenPreview(navHostController = navHostController, viewModel = viewModel)
}

@Composable
fun FeedbackDetailScreenPreview(navHostController: NavHostController, viewModel: MainViewModel) {
    var feedback by remember { mutableStateOf(viewModel.selectedFeedback) }
    var comments by remember { mutableStateOf(listOf<Feedback.Comment>()) }

    LaunchedEffect(Unit) {
        viewModel.getComments(feedback = feedback, onSuccess = {
            comments = it
        }, onFailure = {

        })
    }

    val onStatusChanged: (FeedbackStatus) -> Unit = {
        feedback = feedback.copy(status = it.text)
        viewModel.updateStatus(feedback = feedback, onSuccess = {

        }, onFailure = {

        })
    }

    val onCommentAdded: (String) -> Unit = {
        val comment = Feedback.Comment(
            text = it,
            createdOn = Date(),
            createdBy = UserPref.getUser().userId,
            userName = UserPref.getUser().userName
        )
        viewModel.addComment(feedback = feedback, comment = comment, onSuccess = {
            comments = comments.toMutableList().apply { add(0, comment) }.toList()
        }, onFailure = {

        })
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        HeaderContainer(navHostController = navHostController, feedback = feedback)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = AppTheme.colors.BluePrimary)
        )
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            DescriptionAndStatusContainer(feedback = feedback, onStatusChanged = onStatusChanged)
            CommentContainer(comments = comments, onCommentAdded = onCommentAdded)
        }

    }
}

@Composable
fun CommentContainer(comments: List<Feedback.Comment>, onCommentAdded: (String) -> Unit) {
    var isShowAddComment by remember { mutableStateOf(false) }
    val rotate by animateFloatAsState(targetValue = if (isShowAddComment) 135f else 0f)

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 21.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            AppTheme.colors.LightBluePrimary,
                            AppTheme.colors.WhitePrimary
                        )
                    )
                )
                .padding(Paddings.medium)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = AppTheme.colors.TextBlackPrimary)
                )
                Spacer(space = ItemPaddings.xSmall)
                Text(text = "Comments", style = AppTheme.typography.semiBold15, color = AppTheme.colors.TextBlackPrimary)
            }
            Spacer(space = ItemPaddings.xSmall)
            AddCommentContainer(isShowAddComment) {
                isShowAddComment = false
                onCommentAdded(it)
            }
            CommentListContainer(comments)
            Spacer(space = ItemPaddings.xxLarge)
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(end = Paddings.medium)
                .size(42.dp)
                .rotate(rotate)
                .align(Alignment.TopEnd),
            containerColor = AppTheme.colors.BluePrimary,
            onClick = { isShowAddComment = !isShowAddComment },
            shape = AppTheme.shapes.roundShape
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "", tint = AppTheme.colors.WhitePrimary)
        }
    }
}

@Composable
fun AddCommentContainer(isShowAddComment: Boolean, onCommentAdded: (String) -> Unit) {
    var comment by remember { mutableStateOf("") }

    AnimatedVisibility(visible = isShowAddComment) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(vertical = Paddings.xSmall)
                .shadow(elevation = 1.dp, shape = AppTheme.shapes.medium)
                .background(color = AppTheme.colors.LightBlueSecondary, shape = AppTheme.shapes.medium)
                .padding(Paddings.xxSmall)
        ) {
            Spacer(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(1f)
                    .clip(shape = AppTheme.shapes.small)
                    .background(color = AppTheme.colors.BluePrimary)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.xSmall, horizontal = Paddings.small)
            ) {
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = comment,
                    label = "Add Comment",
                    onTextChanged = { comment = it },
                    multiLine = true
                ) {
                    Icon(modifier = Modifier.clickable {
                        onCommentAdded(comment)
                        comment = ""
                    }, imageVector = Icons.Rounded.Send, contentDescription = "", tint = AppTheme.colors.BluePrimary)
                }
            }
        }
    }

}

@Composable
fun CommentListContainer(comments: List<Feedback.Comment>) {
    val userId = UserPref.getUser().userId

    comments.forEach {
        CommentView(comment = it, isHighLighted = userId == it.createdBy)
    }
}

@Composable
fun CommentView(comment: Feedback.Comment, isHighLighted: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(vertical = Paddings.xSmall)
            .shadow(elevation = 1.dp, shape = AppTheme.shapes.medium)
            .background(color = AppTheme.colors.LightBlueSecondary, shape = AppTheme.shapes.medium)
            .padding(Paddings.xxSmall)
    ) {
        Spacer(
            modifier = Modifier
                .width(12.dp)
                .fillMaxHeight(1f)
                .clip(shape = AppTheme.shapes.small)
                .background(color = if (isHighLighted) AppTheme.colors.BluePrimary else AppTheme.colors.MidBlueSecondary)
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xSmall, horizontal = Paddings.small)
        ) {
            Text(
                text = comment.text,
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary
            )
            Spacer(space = 6)
            Text(modifier = Modifier.fillMaxWidth(), text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append("By ")
                }
                withStyle(style = AppTheme.typography.bold12.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(comment.userName)
                }
                withStyle(style = AppTheme.typography.regular12.toSpanStyle().copy(color = AppTheme.colors.TextBlackSecondary)) {
                    append(" on ${comment.createdOn.inDisplayFormat()}")
                }
            }, textAlign = TextAlign.End)
        }
    }
}


@Composable
fun DescriptionAndStatusContainer(feedback: Feedback, onStatusChanged: (FeedbackStatus) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.medium)
            .padding(horizontal = Paddings.medium)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = feedback.description,
            style = AppTheme.typography.regular15,
            color = AppTheme.colors.TextBlackSecondary
        )

        Spacer(space = ItemPaddings.medium)
        StatusView(modifier = Modifier.fillMaxWidth(), status = feedback.getFeedbackStatus(), onSelect = onStatusChanged)

    }
}

@Composable
fun HeaderContainer(navHostController: NavHostController, feedback: Feedback) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        AppTheme.colors.WhitePrimary,
                        AppTheme.colors.LightBluePrimary
                    )
                )
            )
            .padding(Paddings.medium)
    ) {
        AppBack { navHostController.popBackStack() }
        Spacer(space = ItemPaddings.large)
        Text(text = feedback.shopName, style = AppTheme.typography.bold24, color = AppTheme.colors.TextBlackPrimary)
        Spacer(space = 2)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = AppTheme.typography.regular15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)
                    ) {
                        append("by ")
                    }
                    withStyle(
                        style = AppTheme.typography.semiBold15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)
                    ) {
                        append(feedback.ownerName)
                    }
                }
            )
            Spacer(space = 12)
            Row(
                modifier = Modifier
                    .background(color = AppTheme.colors.LightBluePrimary, shape = AppTheme.shapes.medium)
                    .padding(horizontal = Paddings.small, vertical = Paddings.xSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Rounded.Call,
                    contentDescription = "",
                    tint = AppTheme.colors.BluePrimary
                )
                Spacer(space = ItemPaddings.small)
                Text(text = "Dial Number", color = AppTheme.colors.BluePrimary, style = AppTheme.typography.semiBold12)
            }
        }
        Spacer(space = ItemPaddings.large)
        Text(
            text = feedback.shopAddress + "\n" + feedback.city + " " + feedback.pinCode,
            style = AppTheme.typography.regular12,
            color = AppTheme.colors.TextBlackPrimary
        )
        Spacer(space = 6)
    }
}

@Composable
fun StatusView(modifier: Modifier, status: FeedbackStatus, onSelect: (FeedbackStatus) -> Unit) {
    val statusList = listOf(FeedbackStatus.PENDING, FeedbackStatus.REVIEWED, FeedbackStatus.CLOSED)
    var selected by remember { mutableStateOf(status) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, shape = AppTheme.shapes.medium, color = AppTheme.colors.MidBlueSecondary)
            .padding(Paddings.xxSmall)
    ) {
        statusList.forEach {
            StatusItem(isSelected = selected == it, status = it, onSelect = {
                onSelect(it)
                selected = it
            })
        }
    }
}

@Composable
fun RowScope.StatusItem(isSelected: Boolean, status: FeedbackStatus, onSelect: (FeedbackStatus) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(
                color = if (isSelected) Feedback
                    .getStatusColor(status = status.text)
                    .copy(alpha = 0.25f)
                else AppTheme.colors.WhitePrimary,
                shape = AppTheme.shapes.small
            )
            .padding(vertical = 10.dp)
            .clickable { onSelect(status) },
        text = status.display,
        color = if (isSelected) Feedback.getStatusColor(status = status.text)
        else AppTheme.colors.TextBlackSecondary,
        style = AppTheme.typography.semiBold15,
        textAlign = TextAlign.Center
    )
}