package com.zaed.ordertracker.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    placeHolder: String = "",
    @StringRes errorMessage: Int = 0,
    isError: Boolean = false,
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label =
            if (label.isBlank()) {
                null
            } else {
                { Text(text = label) }
            },
        placeholder =
            if (placeHolder.isNotBlank()) {
                {
                    Text(text = placeHolder)
                }
            } else {
                null
            },
        visualTransformation =
            if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = stringResource(errorMessage),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        trailingIcon = {
            Icon(
                if (showPassword) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                },
                contentDescription = "Toggle password visibility",
                modifier =
                    Modifier
                        .clickable { showPassword = !showPassword },
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = null,
            )
        },
        singleLine = true,
        colors =
            OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
            ),
        shape = RoundedCornerShape(32.dp),
    )
}
