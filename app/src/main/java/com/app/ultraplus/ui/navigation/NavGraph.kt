package com.app.ultraplus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.ultraplus.ui.auth.LoginScreen
import com.app.ultraplus.ui.auth.RegisterScreen
import com.app.ultraplus.ui.auth.SplashScreen
import com.app.ultraplus.viewmodel.AuthViewModel

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    val authViewModel = hiltViewModel<AuthViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        route = MAIN_ROUTE
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navHostController = navController, viewModel = authViewModel)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navHostController = navController, viewModel = authViewModel)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navHostController = navController, viewModel = authViewModel)
        }
    }
}