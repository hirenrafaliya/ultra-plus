package com.app.ultraplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.app.ultraplus.ui.composable.SetStatusBar
import com.app.ultraplus.ui.navigation.SetUpNavGraph
import com.app.ultraplus.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetStatusBar()
            AppTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }
}