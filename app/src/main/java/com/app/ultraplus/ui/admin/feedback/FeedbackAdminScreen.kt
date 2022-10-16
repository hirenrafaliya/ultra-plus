package com.app.ultraplus.ui.admin.feedback

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.dashboard.feedback.feedbackList
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inShortDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun FeedbackAdminScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val user by remember { mutableStateOf(UserPref.getUser()) }

    val context = LocalContext.current
    var startDate by remember {
        mutableStateOf(Calendar.getInstance().apply {
            this[Calendar.DAY_OF_MONTH] = 1
            this[Calendar.MONTH] = 1
            this[Calendar.YEAR] = 2022
        }.time)
    }
    var endDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var startDateDisplay by remember { mutableStateOf("From beginning") }
    var endDateDisplay by remember { mutableStateOf("Today") }

    fun getFeedbacks() =
        run {
            viewModel.getFeedbacks(startDate = startDate, endDate = endDate, onSuccess = {
                viewModel.feedbacks = it
            }, onFailure = {
                Toast.makeText(context, "Error 704 : $it", Toast.LENGTH_SHORT).show()
            })
        }

    val onClickStartDate: () -> Unit = {
        selectDate(context) {
            startDate = it
            startDateDisplay = startDate.inShortDisplayFormat()

            getFeedbacks()
        }
    }
    val onClickEndDate: () -> Unit = {
        selectDate(context) {
            endDate = it
            endDateDisplay = endDate.inShortDisplayFormat()

            getFeedbacks()
        }
    }

    val onClickExport: () -> Unit = {
    }

    LaunchedEffect(Unit) {
        getFeedbacks()
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(Paddings.small),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.LightBluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .clickable { onClickStartDate() }
                            .padding(
                                horizontal = Paddings.small,
                                vertical = Paddings.xxSmall
                            ),
                        text = startDateDisplay,
                        style = AppTheme.typography.semiBold12,
                        color = AppTheme.colors.BluePrimary
                    )
                    Spacer(space = 12)
                    Text(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.LightBlueSecondary,
                                shape = AppTheme.shapes.medium
                            )
                            .padding(
                                horizontal = Paddings.small,
                                vertical = Paddings.xxSmall
                            ),
                        text = "TO",
                        style = AppTheme.typography.semiBold12,
                        color = AppTheme.colors.TextBlackSecondary
                    )
                    Spacer(space = 12)
                    Text(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.LightBluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .clickable { onClickEndDate() }
                            .padding(
                                horizontal = Paddings.small,
                                vertical = Paddings.xxSmall
                            ),
                        text = endDateDisplay,
                        style = AppTheme.typography.semiBold12,
                        color = AppTheme.colors.BluePrimary
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.BluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .clickable { onClickExport() }
                            .padding(
                                horizontal = Paddings.small,
                                vertical = Paddings.xxSmall
                            ),
                        text = "EXPORT +",
                        style = AppTheme.typography.semiBold12,
                        color = AppTheme.colors.WhitePrimary
                    )
                }
            }

            feedbackList(viewModel.feedbacks, {

            })
        }
    }
}

fun selectDate(activity: Context, onSelected: (Date) -> Unit) {
    val date = Calendar.getInstance()
    DatePickerDialog(
        activity,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onSelected(Calendar.getInstance().apply {
                this[Calendar.DAY_OF_MONTH] = mDayOfMonth
                this[Calendar.MONTH] = mMonth
                this[Calendar.YEAR] = mYear
            }.time)
        }, date[Calendar.YEAR], date[Calendar.MONTH], date[Calendar.DAY_OF_MONTH]
    ).show()
}

fun selectTime(activity: Context, onSelected: (Int, Int) -> Unit) {
    val date = Calendar.getInstance()
    TimePickerDialog(
        activity, { _, hourOfDay, minute ->
            onSelected(hourOfDay, minute)
        }, date[Calendar.HOUR_OF_DAY], date[Calendar.MINUTE], false
    ).show()
}