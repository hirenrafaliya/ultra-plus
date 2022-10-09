package com.app.ultraplus.ui.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.viewmodel.AuthViewModel

@Composable
fun SplashScreen(navHostController: NavHostController, viewModel: AuthViewModel) {
    val context = LocalContext.current
//    navHostController.popBackStack()
//    navHostController.navigate(Screen.AdminScreen.route)
    LaunchedEffect(true) {
        if (UserPref.isLoggedIn()) {
            viewModel.fetchUser(userId = UserPref.getUser().userId, onSuccess = {
                // TODO : Check for user active status
                navHostController.popBackStack()
                // TODO : Handle for admin & managers
                navHostController.navigate(Screen.MainScreen.route)
            }, onFailure = {
                Toast.makeText(context, "Error 605 : $it", Toast.LENGTH_SHORT).show()
            })
        } else {
            navHostController.popBackStack()
            navHostController.navigate(Screen.LoginScreen.route)
        }

    }
}