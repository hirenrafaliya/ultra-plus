package com.app.ultraplus.ui.admin.user

import android.view.animation.CycleInterpolator
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.network.model.User
import com.app.ultraplus.network.model.UserStatus
import com.app.ultraplus.network.model.UserType
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.util.inDisplayFormat
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun UserAdminScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    var isShowChangeManagerDialog by remember { mutableStateOf(false) }
    var currentSelectedUser: User? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    val onLogoutClicked = {
        viewModel.logOut(onSuccess = {
            navHostController.navigate(Screen.LoginScreen.route) { popUpTo(0) }
        }, onFailure = {

        })
    }

    LaunchedEffect(Unit) {
        viewModel.getUsers(onSuccess = {
            viewModel.users = it
        }, onFailure = {
            Toast.makeText(context, "Error 707 : $it", Toast.LENGTH_SHORT).show()
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = Paddings.xSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "All Managers",
                        style = AppTheme.typography.bold22,
                        color = AppTheme.colors.TextBlackPrimary
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        Modifier
                            .clickable { onLogoutClicked() }
                            .background(
                                color = AppTheme.colors.LightBluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .padding(horizontal = Paddings.small, vertical = Paddings.xSmall),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(12.dp),
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = "",
                            tint = AppTheme.colors.BluePrimary
                        )
                        Spacer(space = 14)
                        Text(
                            text = "Logout",
                            style = AppTheme.typography.semiBold12,
                            color = AppTheme.colors.BluePrimary
                        )
                    }
                    Spacer(space = 12)
                }
            }

            items(viewModel.users, key = { it.id }) {
                UserView(user = it, onClick = {}, {}, {
                    currentSelectedUser = it
                    isShowChangeManagerDialog = true
                }, onChangeStatusClicked = {
                    val index = viewModel.users.indexOfFirst { user -> it.id == user.id }
                    viewModel.users[index].status = it.status

                    viewModel.updateUser(
                        areaManager = it,
                        onSuccess = {

                        },
                        onFailure = {
                            Toast.makeText(context, "Error 711 : $it", Toast.LENGTH_SHORT).show()
                        })
                })
            }
        }
    }

    if (isShowChangeManagerDialog) {
        ChangeManagerDialog(
            viewModel.users.filter { it.userType == UserType.REPORTING_MANAGER.text },
            currentSelectedUser,
            onReportingManagerSelected = {
                currentSelectedUser = currentSelectedUser?.copy(
                    reportingMangerId = it.id,
                    reportingMangerName = it.userName
                )
                viewModel.updateUser(
                    areaManager = currentSelectedUser!!,
                    onSuccess = {
                        val index = viewModel.users.indexOfFirst { user -> it.id == user.id }
                        viewModel.users[index].reportingMangerId = it.reportingMangerId
                        viewModel.users[index].reportingMangerName = it.reportingMangerName
                        isShowChangeManagerDialog = false
                    },
                    onFailure = {
                        Toast.makeText(context, "Error 712 : $it", Toast.LENGTH_SHORT).show()
                    })
            }
        ) { isShowChangeManagerDialog = false }
    }
}

@Composable
fun LazyItemScope.UserView(
    user: User,
    onClick: (User) -> Unit,
    onClickDialNumber: (User) -> Unit,
    onEditReportingManagerClicked: (User) -> Unit,
    onChangeStatusClicked: (User) -> Unit
) {
    var status by remember { mutableStateOf(user.status) }

    val statusColor by animateColorAsState(
        targetValue = User.getStatusColor(status),
        animationSpec = tween(1000, easing = { CycleInterpolator(10f).getInterpolation(it) })
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(vertical = Paddings.xSmall)
            .shadow(elevation = 1.dp, shape = AppTheme.shapes.medium)
            .background(color = AppTheme.colors.LightBlueSecondary, shape = AppTheme.shapes.medium)
            .clickable { onClick(user) }
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
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .background(
                            color = AppTheme.colors.LightBluePrimary,
                            shape = AppTheme.shapes.medium
                        )
                        .clickable { onClickDialNumber(user) }
                        .padding(horizontal = Paddings.small, vertical = Paddings.xSmall)
                        .size(12.dp),
                    imageVector = Icons.Rounded.Call,
                    contentDescription = "",
                    tint = AppTheme.colors.BluePrimary
                )
                Spacer(space = 12)
                Text(
                    modifier = Modifier,
                    text = user.userName,
                    style = AppTheme.typography.bold16.copy(color = AppTheme.colors.TextBlackPrimary)
                )
                Spacer(space = 12)
                Text(
                    modifier = Modifier,
                    text = "- " + user.email,
                    style = AppTheme.typography.regular12.copy(color = AppTheme.colors.TextBlackSecondary)
                )
            }
            if (user.userType == UserType.AREA_MANAGER.text) {
                Spacer(space = 4)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .background(
                                color = AppTheme.colors.LightBluePrimary,
                                shape = AppTheme.shapes.medium
                            )
                            .clickable { onEditReportingManagerClicked(user) }
                            .padding(horizontal = Paddings.small, vertical = Paddings.xSmall)
                            .size(12.dp),
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "",
                        tint = AppTheme.colors.BluePrimary
                    )
                    Spacer(space = 12)
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = AppTheme.typography.regular12.toSpanStyle()
                                    .copy(color = if (user.reportingMangerId.isNotEmpty()) AppTheme.colors.TextBlackSecondary else AppTheme.colors.StatusRed)
                            ) {
                                val text = if (user.reportingMangerId.isNotEmpty())
                                    append("Managed by ") else append("No managers assigned")
                            }
                            withStyle(
                                style = AppTheme.typography.semiBold12.toSpanStyle()
                                    .copy(color = AppTheme.colors.TextBlackSecondary)
                            ) {
                                append(user.reportingMangerName)
                            }
                        }
                    )
                }
            }
            Spacer(space = 4)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .background(
                            color = AppTheme.colors.LightBluePrimary,
                            shape = AppTheme.shapes.medium
                        )
                        .clickable {
                            val currentStatus = user.status
                            status =
                                if (currentStatus == UserStatus.ACTIVE.text) UserStatus.INACTIVE.text else UserStatus.ACTIVE.text
                            val userCopy =
                                user.copy(status = status)

                            onChangeStatusClicked(userCopy)
                        }
                        .padding(horizontal = Paddings.small, vertical = Paddings.xSmall)
                        .size(12.dp),
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "",
                    tint = AppTheme.colors.BluePrimary
                )
                Spacer(space = 12)
                Text(
                    modifier = Modifier,
                    text = status.uppercase(),
                    style = AppTheme.typography.bold12.copy(color = User.getStatusColor(status = user.status))
                )
            }
            Spacer(space = 4)
            Text(
                text = user.bio,
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary,
                maxLines = 6, overflow = TextOverflow.Clip
            )
            Spacer(space = 4)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Created on ${user.createdOn.inDisplayFormat()}",
                textAlign = TextAlign.End,
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary,
            )
        }
    }
}

@Composable
fun ChangeManagerDialog(
    managers: List<User>,
    user: User?,
    onReportingManagerSelected: (User) -> Unit,
    onClosed: () -> Unit
) {
    Dialog(onDismissRequest = onClosed) {
        ReportingManagerDialog(
            managers = managers,
            currentSelected = managers.filter { it.id == user?.reportingMangerId }.firstOrNull(),
            onClosed = onClosed,
            onReportingManagerSelected = onReportingManagerSelected
        )
    }
}