package com.app.ultraplus.ui.dashboard.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Feedback
import com.app.ultraplus.ui.composable.AppBack
import com.app.ultraplus.ui.composable.AppButton
import com.app.ultraplus.ui.composable.AppTextField
import com.app.ultraplus.ui.composable.Spacer
import com.app.ultraplus.ui.theme.AppTheme
import com.app.ultraplus.ui.theme.ItemPaddings
import com.app.ultraplus.ui.theme.Paddings
import com.app.ultraplus.viewmodel.MainViewModel
import java.util.*

@Composable
fun AddFeedbackScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    AddFeedbackScreenPreview(navHostController = navHostController, viewModel = viewModel)
}

@Composable
fun AddFeedbackScreenPreview(navHostController: NavHostController, viewModel: MainViewModel) {

    var isLoading by remember { mutableStateOf(false) }

    var shopName by remember { mutableStateOf("") }
    var shopKeeperName by remember { mutableStateOf("") }
    var shopKeeperNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }

    val onAddFeedbackClicked: () -> Unit = {
        val currentDate = Date()
        val fb = Feedback(
            shopName = shopName,
            ownerName = shopKeeperName,
            ownerNumber = shopKeeperNumber,
            shopAddress = address,
            city = city,
            pinCode = postalCode,
            description = feedback.replace("\\n", "<br />"),
            createdBy = UserPref.getUser().userId,
            updatedOn = currentDate,
            createdOn = currentDate,
            userName = UserPref.getUser().userName
        )
        isLoading = true
        viewModel.addFeedback(feedback = fb, onSuccess = {
            isLoading = false
            navHostController.popBackStack()
        }, onFailure = {
            isLoading = false
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Paddings.medium),
    ) {
        AppBack { navHostController.popBackStack() }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Add Feedback", style = AppTheme.typography.bold36)
            Spacer(space = ItemPaddings.large)
            AppTextField(
                text = shopName,
                label = "Shop name",
                onTextChanged = { shopName = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = shopKeeperName,
                label = "Shopkeeper name",
                onTextChanged = { shopKeeperName = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = shopKeeperNumber,
                label = "Shopkeeper phone number",
                onTextChanged = { shopKeeperNumber = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            AppTextField(
                text = address,
                label = "Address",
                onTextChanged = { address = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )
            Spacer(space = ItemPaddings.medium)
            Row(Modifier.fillMaxWidth()) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = Paddings.small),
                    text = city,
                    label = "City",
                    onTextChanged = { city = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                )

                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = Paddings.small),
                    text = postalCode,
                    label = "Postal code",
                    onTextChanged = { postalCode = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )
            }

            Spacer(space = ItemPaddings.medium)
            AppTextField(modifier = Modifier,
                text = feedback,
                label = "Feedback",
                multiLine = true,
                onTextChanged = { feedback = it }
            )
            Spacer(space = 200)
        }
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.WhitePrimary)
                    .padding(top = ItemPaddings.xSmall.dp),
                text = "Add Feedback ->",
                isLoading = isLoading
            ) {
                onAddFeedbackClicked()
            }
        }

    }
}