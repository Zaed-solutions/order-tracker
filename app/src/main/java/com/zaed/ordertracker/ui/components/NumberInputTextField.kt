package com.zaed.ordertracker.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun NumberInputTextField(
    modifier: Modifier = Modifier,
    value: Double = 0.0,
    onValueChange: (Double) -> Unit = {},
    label: String = "",
    placeHolder: String = "",
    supportingText: String = "",
    imageVector: ImageVector? = null,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    @StringRes
    errorMessage: Int = 0,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    withBorder: Boolean = false,
    shape: Shape = RoundedCornerShape(32.dp),
    containerColor: Color = MaterialTheme.colorScheme.background,
    keyboardType: KeyboardType = KeyboardType.Decimal,
) {
    var textValue by remember { mutableStateOf(value.toString()) }
    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = if (value == 0.0) "" else textValue,
        onValueChange = { newText ->
            val formattedText = newText.replace(',', '.') // Allow comma as decimal
            if (formattedText.isEmpty()) {
                textValue = ""
                onValueChange(0.0)
            } else if (formattedText.matches(Regex("^\\d*\\.?\\d*\$"))) {
                textValue = formattedText
                onValueChange(formattedText.toDoubleOrNull() ?: 0.0)
            }
        },
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
        colors =
            OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (withBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
                focusedBorderColor = if (withBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
                unfocusedContainerColor = containerColor,
                focusedContainerColor = containerColor,
            ),
        leadingIcon =
            if (imageVector != null) {
                {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                    )
                }
            } else {
                null
            },
        shape = shape,
        enabled = enabled,
        isError = isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        supportingText =
            if (isError) {
                {
                    Text(
                        text = stringResource(errorMessage),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            } else if (supportingText.isNotBlank()) {
                {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            } else {
                null
            },
    )
}

@Composable
fun NumberInputTextField(
    modifier: Modifier = Modifier,
    value: Int = 0,
    onValueChange: (Int) -> Unit = {},
    label: String = "",
    placeHolder: String = "",
    supportingText: String = "",
    imageVector: ImageVector? = null,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    @StringRes
    errorMessage: Int = 0,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    withBorder: Boolean = false,
    shape: Shape = RoundedCornerShape(32.dp),
    containerColor: Color = MaterialTheme.colorScheme.background,
    keyboardType: KeyboardType = KeyboardType.Decimal,
) {
    var textValue by remember { mutableStateOf(value.toString()) }
    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = if (value == 0) "" else textValue,
        onValueChange = { newText ->
            val formattedText = newText.replace(',', '.') // Allow comma as decimal
            if (formattedText.isEmpty()) {
                textValue = ""
                onValueChange(0)
            } else if (formattedText.matches(Regex("^\\d+\$"))) {
                textValue = formattedText
                onValueChange(formattedText.toIntOrNull() ?: 0)
            }
        },
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
        colors =
            OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (withBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
                focusedBorderColor = if (withBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
                unfocusedContainerColor = containerColor,
                focusedContainerColor = containerColor,
            ),
        leadingIcon =
            if (imageVector != null) {
                {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null,
                    )
                }
            } else {
                null
            },
        shape = shape,
        enabled = enabled,
        isError = isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        supportingText =
            if (isError) {
                {
                    Text(
                        text = stringResource(errorMessage),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            } else if (supportingText.isNotBlank()) {
                {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            } else {
                null
            },
    )
}
