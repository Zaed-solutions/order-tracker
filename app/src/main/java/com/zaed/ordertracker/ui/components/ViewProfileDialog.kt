package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.User

@Composable
fun ViewProfileDialog(
    modifier: Modifier = Modifier,
    initialUser: User,
    onDismissRequest: () -> Unit,
) {
    var user by remember { mutableStateOf(initialUser) }
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.profile),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(88.dp).padding(top = 16.dp),
                )
                TextInputTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = user.username,
                    readOnly = true,
                    onValueChange = {
                    },
                    label = stringResource(R.string.username),
                )
                TextInputTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = user.password,
                    readOnly = true,
                    onValueChange = {
                    },
                    label = stringResource(R.string.password),
                )
                Button(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    onClick = {
                        onDismissRequest()
                    },
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}
