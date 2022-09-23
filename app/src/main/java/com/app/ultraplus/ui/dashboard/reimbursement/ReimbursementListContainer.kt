package com.app.ultraplus.ui.dashboard.reimbursement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun ReimbursementListContainer(navHostController: NavHostController, viewModel: MainViewModel) {

    ReimbursementListContainerPreview(navHostController = navHostController, viewModel = viewModel)
}

@Composable
fun ReimbursementListContainerPreview(navHostController: NavHostController, viewModel: MainViewModel) {

    val userName by remember { mutableStateOf(UserPref.getUser().userName) }
    var reimbursements by remember { mutableStateOf(listOf<Reimbursement>()) }

    LaunchedEffect(Unit) {
        viewModel.getReimbursements(onSuccess = {
            reimbursements = it
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
                Spacer(space = ItemPaddings.xSmall)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = AppTheme.colors.MidBlueSecondary)
                )
                Spacer(space = ItemPaddings.small)
                Text(text = "Reimbursements", style = AppTheme.typography.bold22, color = AppTheme.colors.TextBlackPrimary)
                Spacer(space = ItemPaddings.xxSmall)
            }
        }
        reimbursementList(reimbursements = reimbursements)


    }
}

fun LazyListScope.reimbursementList(reimbursements: List<Reimbursement>) {
    items(reimbursements) {
        ReimbursementView(it)
    }
}

@Composable
fun ReimbursementView(reimbursement: Reimbursement) {
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
            Text(text = buildAnnotatedString {
                withStyle(style = AppTheme.typography.bold24.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(reimbursement.distance.toString())
                }
                withStyle(style = AppTheme.typography.semiBold15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(" " + reimbursement.unit)
                }
                withStyle(style = AppTheme.typography.regular15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(" by ")
                }
                withStyle(style = AppTheme.typography.semiBold15.toSpanStyle().copy(color = AppTheme.colors.TextBlackPrimary)) {
                    append(reimbursement.userName)
                }
            })
            Spacer(space = 6)
            Text(
                text = "on ${reimbursement.createdOn.inDisplayFormat()}",
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary
            )
        }
    }
}