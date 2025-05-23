package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Dialog for editing an existing MP Group
 */
@Composable
fun EditMpGroupDialog(
    tempMpGroupName: String,
    tempMpGroupColor: Int,
    onMpGroupNameChange: (String) -> Unit,
    onMpGroupColorChange: (Int) -> Unit,
    canEditName: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit MP Group") },
        text = {
            Column {
                OutlinedTextField(
                    enabled = canEditName,
                    value = tempMpGroupName,
                    onValueChange = onMpGroupNameChange,
                    label = { Text("MP Group Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Select Color")
                Spacer(modifier = Modifier.height(8.dp))
                ColorPickerRow(
                    selectedColor = tempMpGroupColor,
                    onColorSelected = onMpGroupColorChange
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}