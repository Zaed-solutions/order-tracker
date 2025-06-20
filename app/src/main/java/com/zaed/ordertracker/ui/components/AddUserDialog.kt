package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.User

/**
 * Dialog for adding a new user
 */
@Composable
fun AddUserDialog(
    tempUsername: String,
    tempPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    var usernameError by remember { mutableStateOf(false to 0) }
    var passwordError by remember { mutableStateOf(false to 0) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New User") },
        text = {
            Column {
                TextInputTextField(
                    value = tempUsername,
                    onValueChange = {
                        if (it.isUpperCase()) {
                            onUsernameChange
                        }
                    },
                    label = stringResource(R.string.username),
                    isError = usernameError.first,
                    errorMessage = usernameError.second,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextField(
                    value = tempPassword,
                    onValueChange = onPasswordChange,
                    label = stringResource(R.string.password),
                    isError = passwordError.first,
                    errorMessage = passwordError.second,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                usernameError = User.validateUsername(tempUsername)
                passwordError = User.validatePassword(tempPassword)
                if (!usernameError.first && !passwordError.first) {
                    onConfirm()
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

fun String.isUpperCase(): Boolean = this.all { it.isUpperCase() }
