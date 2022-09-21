package com.app.ultraplus.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.ultraplus.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onTextChanged: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    multiLine: Boolean = false,
    enabled: Boolean = true
) {

    @Composable
    fun getBorderColor() = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = AppTheme.colors.MidBluePrimary,
        focusedBorderColor = AppTheme.colors.BluePrimary,
        focusedLabelColor = AppTheme.colors.BluePrimary,
        unfocusedLabelColor = AppTheme.colors.TextBlackSecondary,
        textColor = AppTheme.colors.TextBlackPrimary
    )

    OutlinedTextField(
        colors = getBorderColor(),
        value = text,
        shape = AppTheme.shapes.medium,
        onValueChange = onTextChanged,
        label = {
            CompositionLocalProvider() {
                Text(text = label, style = AppTheme.typography.regular15)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(if (multiLine) Dp.Companion.Unspecified else 60.dp),
        textStyle = AppTheme.typography.semiBold15,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled
    )
}