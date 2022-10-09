package com.app.ultraplus.ui.dashboard.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.ui.composable.AppButton
import com.app.ultraplus.ui.composable.AppTextField
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    val user by remember { mutableStateOf(UserPref.getUser()) }
    val context = LocalContext.current

    val onLogOutClicked: () -> Unit = {
        viewModel.logOut(onSuccess = {
            navHostController.clearBackStack(0)
            navHostController.navigate(Screen.LoginScreen.route)
        }, onFailure = {
            Toast.makeText(context, "Error 710 : $it", Toast.LENGTH_SHORT).show()
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 44.dp)
            .padding(Paddings.medium)
            .verticalScroll(rememberScrollState())
            .background(color = AppTheme.colors.WhitePrimary)
    ) {
        Text(text = "Add Feedback", style = AppTheme.typography.bold36)
        Spacer(space = ItemPaddings.large)
        AppTextField(text = user.userName, label = "Name", enabled = false)
        Spacer(space = ItemPaddings.small)
        AppTextField(text = user.phoneNumber, label = "Phone number", enabled = false)
        Spacer(space = ItemPaddings.small)
        AppTextField(text = user.email, label = "Email", enabled = false)
        Spacer(space = ItemPaddings.small)
        AppTextField(text = user.bio, label = "Bio", enabled = false)
        Spacer(space = ItemPaddings.large)
        AppButton(text = "Logout ->", onClick = onLogOutClicked)
    }
}