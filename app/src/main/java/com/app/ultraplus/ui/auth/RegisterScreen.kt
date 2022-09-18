package com.app.ultraplus.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.app.ultraplus.composable.AppButton
import com.app.ultraplus.composable.AppTextField
import com.app.ultraplus.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.viewmodel.AuthViewModel
import com.google.firebase.annotations.PreviewApi

@Composable
fun RegisterScreen(navHostController: NavHostController, viewModel: AuthViewModel) {

    RegisterScreenPreview(navHostController, viewModel)
}

@PreviewApi
@Composable
fun RegisterScreenPreview(navHostController: NavHostController, viewModel: AuthViewModel) {
    val userTypes = listOf("Area manager", "Reporting manager")

    var userType by remember { mutableStateOf(userTypes[0]) }
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val onUserTypeChanged = { type: String -> userType = type }
    val onRegisterClicked = {

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.WhitePrimary)
            .padding(Paddings.medium),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
            Text(text = "Register as...", style = AppTheme.typography.bold36)
            Spacer(space = ItemPaddings.large)
            SelectorSwitch(items = userTypes, selected = userType, onSelect = onUserTypeChanged)
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = name,
                label = "Name",
                onTextChanged = { name = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = phoneNumber,
                label = "Phone number",
                onTextChanged = { phoneNumber = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = email,
                label = "Email",
                onTextChanged = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = bio,
                label = "Bio",
                onTextChanged = { bio = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = password,
                label = "Password",
                onTextChanged = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = confirmPassword,
                label = "Confirm password",
                onTextChanged = { confirmPassword = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )
            Spacer(space = ItemPaddings.large)
            AppButton(text = "Register as $userType +", onClick = onRegisterClicked)
        }
    }
}

@Composable
fun SelectorSwitch(modifier: Modifier = Modifier, items: List<String>, selected: String, onSelect: (String) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.LightBluePrimary, shape = AppTheme.shapes.medium)
    ) {
        Row(Modifier.fillMaxWidth()) {
            items.forEach {
                val isSelected = selected == it
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(Paddings.xSmall)
                        .background(
                            color = if (isSelected) AppTheme.colors.WhitePrimary else AppTheme.colors.LightBluePrimary,
                            shape = AppTheme.shapes.medium
                        )
                        .clickable(onClick = { onSelect(it) })
                        .padding(Paddings.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it, style = AppTheme.typography.regular15, color = AppTheme.colors.TextBlackPrimary)
                }
            }
        }
    }
}