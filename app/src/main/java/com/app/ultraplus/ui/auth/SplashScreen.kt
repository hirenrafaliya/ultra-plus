package com.app.ultraplus.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.viewmodel.AuthViewModel

@Composable
fun SplashScreen(navHostController: NavHostController, viewModel: AuthViewModel) {

    LaunchedEffect(true) {
        if (UserPref.isLoggedIn()) {
            viewModel.fetchUser(userId = UserPref.getUser().userId, onSuccess = {
                navHostController.popBackStack()
                navHostController.navigate(Screen.MainScreen.route)
            }, onFailure = {

            })
        } else {
            navHostController.popBackStack()
            navHostController.navigate(Screen.LoginScreen.route)
        }

    }
}