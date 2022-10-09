package com.app.ultraplus.ui.admin.reimbursment

import android.app.DatePickerDialog
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
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.dashboard.feedback.feedbackList
import com.app.ultraplus.ui.dashboard.reimbursement.reimbursementList
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inShortDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel
import java.util.*

@Composable
fun ReimbursementAdminScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val user by remember { mutableStateOf(UserPref.getUser()) }

    val activity = LocalContext.current
    var startDate by remember { mutableStateOf(Calendar.getInstance().apply {
        this[Calendar.DAY_OF_MONTH] = 1
        this[Calendar.MONTH] = 1
        this[Calendar.YEAR] = 2022
    }.time) }
    var endDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var startDateDisplay by remember { mutableStateOf("From beginning") }
    var endDateDisplay by remember { mutableStateOf("Today") }

    fun getReimbursement() =
        run {
            viewModel.getReimbursement(startDate = startDate, endDate = endDate, onSuccess = {
                viewModel.reimbursements = it
            }, onFailure = {
                Toast.makeText(activity, "Error 705 : $it", Toast.LENGTH_SHORT).show()
            })
        }

    val onClickStartDate: () -> Unit = {
        selectDate(activity) {
            startDate = it
            startDateDisplay = startDate.inShortDisplayFormat()

            getReimbursement()
        }
    }
    val onClickEndDate: () -> Unit = {
        selectDate(activity) {
            endDate = it
            endDateDisplay = endDate.inShortDisplayFormat()

            getReimbursement()
        }
    }

    LaunchedEffect(Unit) {
        getReimbursement()
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
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Text(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.BluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .clickable { }
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

            reimbursementList(viewModel.reimbursements)
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