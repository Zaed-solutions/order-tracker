package com.zaed.ordertracker.ui.flightdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R

@Composable
fun ConfirmNavigateToLoginDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.you_need_to_login_first_to_continue),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.some_operation_requires_login),
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                modifier =
                    Modifier
                        .widthIn(min = 100.dp)
                        .padding(end = 8.dp)
                        .weight(1f),
                onClick = { onDismiss() },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
            ) {
                Text(text = stringResource(R.string.dismiss))
            }
            Button(
                modifier =
                    Modifier
                        .widthIn(min = 100.dp)
                        .weight(1f),
                onClick = { onConfirm() },
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}
