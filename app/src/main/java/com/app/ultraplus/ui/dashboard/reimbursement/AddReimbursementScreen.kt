package com.app.ultraplus.ui.dashboard.reimbursement

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.ultraplus.local.UserPref
import com.app.ultraplus.network.model.Reimbursement
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
fun AddReimbursementScreen(navHostController: NavHostController, viewModel: MainViewModel) {

    AddReimbursementScreenPreview(navHostController = navHostController, viewModel = viewModel)
}

@Composable
fun AddReimbursementScreenPreview(navHostController: NavHostController, viewModel: MainViewModel) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var distance by remember { mutableStateOf("0.0") }

    val onAddReimbursementClicked: () -> Unit = {
        val currentDate = Date()
        val reimbursement = Reimbursement(
            createdOn = currentDate,
            updatedOn = currentDate,
            createdBy = UserPref.getUser().userId,
            userName = UserPref.getUser().userName,
            distance = distance.toFloatOrNull() ?: 0.0f,
            unit = "Kilometer"
        )

        isLoading = true
        viewModel.addReimbursement(reimbursement = reimbursement, onSuccess = {
            isLoading = false
            navHostController.popBackStack()
        }, onFailure = {
            isLoading = false
            Toast.makeText(context, "Error 702 : $it", Toast.LENGTH_SHORT).show()
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
            Text(text = "Add Reimbursement", style = AppTheme.typography.bold36)
            Spacer(space = ItemPaddings.large)
            Row(Modifier.fillMaxWidth()) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = Paddings.small),
                    text = distance,
                    label = "Distance",
                    onTextChanged = { distance = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
                )

                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = Paddings.small),
                    text = "Kilometer",
                    label = "Unit",
                    onTextChanged = { },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = false
                )
            }
        }

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.WhitePrimary)
                    .padding(top = ItemPaddings.xSmall.dp),
                text = "Add Reimbursement +",
                isLoading = isLoading
            ) {
                onAddReimbursementClicked()
            }
        }
    }
}