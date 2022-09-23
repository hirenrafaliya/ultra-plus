package com.app.ultraplus.ui.navigation

const val MAIN_ROUTE = "main"
const val APP_ROUTE = "app"

sealed class Screen(val route: String) {

    object SplashScreen : Screen("Splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")

    object MainScreen : Screen("main_screen")
    object AddFeedbackScreen : Screen("add_feedback_screen")
    object AddReimbursementScreen : Screen("add_reimbursement_screen")

    object FeedbackDetailScreen : Screen("feedback_detail_screen")


}