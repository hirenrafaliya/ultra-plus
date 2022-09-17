package com.app.ultraplus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetUpNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = MAIN_ROUTE,
        route = MAIN_ROUTE
    ) {
        appNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.appNavGraph(navController: NavHostController){
    composable(route = Screen.SplashScreen.route) {

    }
    composable(route = Screen.LoginScreen.route) {

    }
    composable(route = Screen.RegisterScreen.route) {

    }
}