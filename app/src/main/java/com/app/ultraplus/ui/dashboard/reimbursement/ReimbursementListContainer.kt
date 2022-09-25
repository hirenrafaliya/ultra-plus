package com.app.ultraplus.ui.dashboard.reimbursement

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Reimbursement
import com.app.ultraplus.network.model.UserType
import com.app.ultraplus.ui.composable.AppFab
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun ReimbursementListContainer(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    val user by remember { mutableStateOf(UserPref.getUser()) }

    LaunchedEffect(Unit) {
        viewModel.getReimbursements(onSuccess = {
            viewModel.reimbursements = it
        }, onFailure = {

        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        LazyColumn(
            modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.medium)
                .padding(top = Paddings.medium),
            contentPadding = PaddingValues(bottom = ItemPaddings.xxxLarge.dp)
        ) {

            item {
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
                    Spacer(space = ItemPaddings.small)
                    Text(
                        text = "Reimbursements",
                        style = AppTheme.typography.bold22,
                        color = AppTheme.colors.TextBlackPrimary
                    )
                    Spacer(space = ItemPaddings.xxSmall)
                }
            }
            reimbursementList(reimbursements = viewModel.reimbursements)


        }

        if (user.userType == UserType.AREA_MANAGER.text)
            AppFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 66.dp, end = Paddings.large)
            ) {
                navHostController.navigate(Screen.AddReimbursementScreen.route)
            }
    }
}

fun LazyListScope.reimbursementList(reimbursements: List<Reimbursement>) {
    items(reimbursements, key = { it.id }) {
        ReimbursementView(it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ReimbursementView(reimbursement: Reimbursement) {
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
                withStyle(
                    style = AppTheme.typography.bold24.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
                    append(reimbursement.distance.toString())
                }
                withStyle(
                    style = AppTheme.typography.semiBold15.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
                    append(" " + reimbursement.unit)
                }
                withStyle(
                    style = AppTheme.typography.regular15.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
                    append(" by ")
                }
                withStyle(
                    style = AppTheme.typography.semiBold15.toSpanStyle()
                        .copy(color = AppTheme.colors.TextBlackPrimary)
                ) {
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