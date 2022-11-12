package com.app.ultraplus.ui.dashboard.feedback

import android.view.animation.CycleInterpolator
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.UserType
import com.app.ultraplus.ui.admin.feedback.selectDate
import com.app.ultraplus.ui.admin.feedback.selectTime
import com.app.ultraplus.ui.admin.user.SelectableReportingManagerView
import com.app.ultraplus.ui.composable.*
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.util.inFormat
import com.app.ultraplus.util.inShortDisplayFormat
import com.app.ultraplus.util.scheduleReminder
import com.app.ultraplus.viewmodel.MainViewModel
import io.iamjosephmj.flinger.bahaviours.StockFlingBehaviours
import java.util.Calendar

@Composable
fun FeedbackListContainer(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    val user by remember { mutableStateOf(UserPref.getUser()) }
    val context = LocalContext.current
    var showReminderDialog by remember { mutableStateOf(false) }
    val managers = remember { mutableStateListOf<Pair<String, String>>() }
    var selectedManagerId = remember { mutableStateListOf<String>() }

    val onClickReminder: () -> Unit = {
        showReminderDialog = true
    }

    LaunchedEffect(Unit) {
        viewModel.getFeedbacks(onSuccess = {
            viewModel.feedbacks = it
            managers.clear()
            it.map {
                Pair(it.createdBy, it.userName)
            }.forEach {
                if (!managers.contains(it)) {
                    managers.add(it)
                }
            }
        }, onFailure = {
            Toast.makeText(context, "Error 703 : $it", Toast.LENGTH_SHORT).show()
        })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.medium)
                .padding(top = Paddings.medium),
            contentPadding = PaddingValues(bottom = ItemPaddings.xxxLarge.dp),
            flingBehavior = StockFlingBehaviours.smoothScroll()
        ) {

            item {
                Column(Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Hello ${user.userName}",
                                style = AppTheme.typography.regular15,
                                color = AppTheme.colors.TextBlackPrimary
                            )
                            Text(
                                text = "Good Afternoon",
                                style = AppTheme.typography.semiBold22,
                                color = AppTheme.colors.TextBlackPrimary
                            )
                            Spacer(space = ItemPaddings.xSmall)
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = AppTheme.colors.MidBlueSecondary)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(12.dp)
                                .background(
                                    color = AppTheme.colors.LightBluePrimary,
                                    shape = AppTheme.shapes.medium
                                )
                                .clickable { onClickReminder() }
                                .padding(horizontal = Paddings.small, vertical = Paddings.xSmall),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Rounded.Notifications,
                                contentDescription = "",
                                tint = AppTheme.colors.BluePrimary
                            )
                            Spacer(space = ItemPaddings.small)
                            Text(
                                text = "Reminder",
                                color = AppTheme.colors.BluePrimary,
                                style = AppTheme.typography.semiBold12
                            )
                        }
                    }

                    Spacer(space = ItemPaddings.small)
                    Text(
                        text = "Feedbacks",
                        style = AppTheme.typography.bold22,
                        color = AppTheme.colors.TextBlackPrimary
                    )
                    Spacer(space = ItemPaddings.xxSmall)
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = Paddings.xSmall)
                ) {
                    AnimatedVisibility(selectedManagerId.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .background(
                                    AppTheme.colors.LightBluePrimary,
                                    AppTheme.shapes.medium
                                )
                                .clickable {
                                    selectedManagerId.clear()
                                }
                                .padding(Paddings.xSmall),
                            text = "Clear",
                            style = AppTheme.typography.regular12,
                            color = AppTheme.colors.BluePrimary
                        )
                        Spacer(space = 8)
                    }

                    managers.forEach {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = Paddings.xSmall)
                                .background(
                                    if (selectedManagerId.contains(it.first)) AppTheme.colors.BluePrimary else AppTheme.colors.LightBluePrimary,
                                    AppTheme.shapes.medium
                                )
                                .clickable {
                                    if (selectedManagerId.contains(it.first)) selectedManagerId.remove(
                                        it.first
                                    )
                                    else selectedManagerId.add(it.first)
                                }
                                .padding(Paddings.xSmall),
                            text = it.second,
                            style = AppTheme.typography.regular12,
                            color = if (selectedManagerId.contains(it.first)) AppTheme.colors.WhitePrimary else AppTheme.colors.BluePrimary
                        )
                    }
                }
            }


            feedbackList(feedbacks = viewModel.feedbacks.filter { feedback ->
                if (selectedManagerId.isNotEmpty()) {
                    selectedManagerId.contains(feedback.createdBy)
                } else true
            }, onClick = {
                viewModel.selectedFeedback = it
                navHostController.navigate(Screen.FeedbackDetailScreen.route)
            })


        }

        if (user.userType == UserType.AREA_MANAGER.text)
            AppFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 66.dp, end = Paddings.large)
            ) {
                navHostController.navigate(Screen.AddFeedbackScreen.route)
            }
    }

    if (showReminderDialog) {
        Dialog(onDismissRequest = { showReminderDialog = false }) {
            ReminderDialog {
                showReminderDialog = false
            }
        }
    }
}

@Composable
fun ReminderDialog(onClosed: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }

    var isDateSelected by remember { mutableStateOf(false) }
    var isTimeSelected by remember { mutableStateOf(false) }

    val date by remember {
        mutableStateOf(Calendar.getInstance())
    }

    val context = LocalContext.current

    DialogWindow(title = "Schedule Reminder") {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AppTextField(text = title, label = "Title", onTextChanged = { title = it })
            Spacer(space = 8)
            Row(Modifier.fillMaxWidth()) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = if (!isDateSelected) "Set Date" else date.time.inFormat("dd MMM")
                ) {
                    showDateDialog = true
                }
                Spacer(space = 4)
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = if (!isTimeSelected) "Set Time" else date.time.inFormat("hh:mm")
                ) {
                    showTimeDialog = true
                }
            }
            AppButton(text = "Set Reminder +") {
                if (isDateSelected && isTimeSelected) {
                    scheduleReminder(context, title, date.timeInMillis)
                    Toast.makeText(context, "Reminder scheduled successfully", Toast.LENGTH_SHORT)
                        .show()
                    onClosed()
                }
            }
        }
    }

    if (showDateDialog) {
        selectDate(context) {
            date.time = it
            isDateSelected = true
            showDateDialog = false
        }
    }

    if (showTimeDialog) {
        selectTime(context) { hour, minute ->
            date.set(Calendar.HOUR_OF_DAY, hour)
            date.set(Calendar.MINUTE, minute)
            date.set(Calendar.SECOND, 0)

            isTimeSelected = true
            showTimeDialog = false
        }
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
        animationSpec = tween(1000, easing = { CycleInterpolator(10f).getInterpolation(it) })
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(animationSpec = tween(800, easing = FastOutSlowInEasing))
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
                withStyle(
                    style = AppTheme.typography.bold16.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
                    append(feedback.shopName + " ")
                }
                withStyle(
                    style = AppTheme.typography.regular12.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackSecondary)
                ) {
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
                withStyle(
                    style = AppTheme.typography.regular12.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackSecondary)
                ) {
                    append("By ")
                }
                withStyle(
                    style = AppTheme.typography.bold12.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
                    append(feedback.userName + " ")
                }
                withStyle(
                    style = AppTheme.typography.regular12.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackSecondary)
                ) {
                    append("on " + feedback.createdOn.inDisplayFormat())
                }
            })

        }
    }
}