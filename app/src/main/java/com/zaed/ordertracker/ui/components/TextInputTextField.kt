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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextInputTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    placeHolder: String = "",
    supportingText: String = "",
    imageVector: ImageVector? = null,
    @StringRes
    errorMessage: Int = 0,
    readOnly: Boolean = false,
    isError: Boolean = false,
    withBorder: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.background,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: Shape = RoundedCornerShape(32.dp),
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
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
        isError = isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
