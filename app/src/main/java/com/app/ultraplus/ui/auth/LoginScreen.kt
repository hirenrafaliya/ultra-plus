package com.app.ultraplus.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.app.ultraplus.ui.composable.AppButton
import com.app.ultraplus.ui.composable.AppTextField
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.navigation.Screen
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.viewmodel.AuthViewModel
import com.google.firebase.annotations.PreviewApi

@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: AuthViewModel) {

    LoginScreenPreview(navHostController, viewModel)
}

@PreviewApi
@Composable
fun LoginScreenPreview(navHostController: NavHostController, viewModel: AuthViewModel) {
    var number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val onRegisterClicked = {
        navHostController.navigate(Screen.RegisterScreen.route)
    }

    val onLoginClicked: () -> Unit = {
        isLoading = true
        viewModel.loginUser(number = number, password = password, onSuccess = {
            isLoading = false
            navHostController.navigate(Screen.MainScreen.route) { popUpTo(0) }
        }, onFailure = {
            isLoading = false
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
            .padding(Paddings.medium),
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Login", style = AppTheme.typography.bold36)
            Spacer(ItemPaddings.large)
            AppTextField(
                text = number,
                label = "Phone Number",
                onTextChanged = { number = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {

                })
            )
            Spacer(ItemPaddings.medium)
            AppTextField(
                text = password, label = "Password", onTextChanged = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onDone = { })
            )
            Spacer(ItemPaddings.xxLarge)
            AppButton(text = "Login to Ultra Plus ->", onClick = onLoginClicked, isLoading = isLoading)
            Spacer(ItemPaddings.medium)
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "Don't have an account ?",
                style = AppTheme.typography.regular12,
                color = AppTheme.colors.TextBlackSecondary
            )
            AppButton(text = "Create a new account +", onClick = onRegisterClicked)
        }
    }
}