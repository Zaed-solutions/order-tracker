package com.zaed.ordertracker.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup

@Composable
fun MpGroupContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            // Group header section
            Text(
                text = "MP Groups",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Group tabs
            MpGroupTabs(
                groups = uiState.groups,
                selectedGroupId = uiState.selectedGroupId,
                onGroupSelected = { groupId ->
                    onAction(HomeUiAction.SelectGroup(groupId))
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Selected group content
            uiState.selectedGroupId?.let { selectedGroupId ->
                val selectedGroup = uiState.groups.find { it.id == selectedGroupId }
                if (selectedGroup != null) {
                    MpGroupDetails(
                        group = selectedGroup,
                        onAction = onAction
                    )
                }
            }
        }
    }
}
@Composable
fun MpGroupTabs(
    groups: List<MpGroup>,
    selectedGroupId: String?,
    onGroupSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(groups) { group ->
            val isSelected = group.id == selectedGroupId

            MpGroupTab(
                group = group,
                isSelected = isSelected,
                onClick = { onGroupSelected(group.id) }
            )
        }
    }
}
@Composable
fun MpGroupTab(
    group: MpGroup,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(48.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) {
            Color(group.color).copy(alpha = 0.7f)
        } else {
            Color(group.color).copy(alpha = 0.3f)
        },
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )

            Spacer(modifier = Modifier.width(8.dp))

            // PND count badge
            if (group.pndCount > 0) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.error
                ) {
                    Text(
                        text = group.pndCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun MpGroupDetails(
    group: MpGroup,
    onAction: (HomeUiAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Group header with color selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge
            )

            // Color picker button
            ColorPickerButton(
                currentColor = group.color,
                onColorSelected = { color ->
                    onAction(HomeUiAction.UpdateGroupBackgroundColor(group.id, color))
                }
            )
        }

        // Master Packages list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Master Packages",
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Add MP button
                    Button(
                        onClick = {
                            // Show add MP dialog
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Master Package"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Add MP")
                    }
                }
            }

            items(group.masterPackages) { masterPackage ->
                MasterPackageItem(
                    masterPackage = masterPackage,
                    onEdit = {
                        // Show edit dialog
                    },
                    onExport = { mpId ->
                        onAction(HomeUiAction.ExportMasterPackages(group.id, listOf(mpId)))
                    }
                )
            }

            // If list is empty
            if (group.masterPackages.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Master Packages in this group",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ColorPickerButton(
    currentColor: Int,
    onColorSelected: (Int) -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showColorPicker = true },
        modifier = Modifier
            .size(40.dp)
            .background(
                color = Color(currentColor),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Change color",
            tint = contentColorFor(backgroundColor = Color(currentColor))
        )
    }

    if (showColorPicker) {
        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            title = { Text("Select Group Color") },
            text = {
                ColorPicker(
                    onColorSelected = { color ->
                        onColorSelected(color)
                        showColorPicker = false
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = { showColorPicker = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
fun ColorPicker(
    onColorSelected: (Int) -> Unit
) {
    // Predefined colors based on the requirements (G, M, B, U)
    val colors = listOf(
        0xFF000000.toInt(), // Black
        0xFFE91E63.toInt(), // Pink
        0xFFF44336.toInt(), // Red
        0xFFFF9800.toInt(), // Orange
        0xFFFFEB3B.toInt(), // Yellow
        0xFF4CAF50.toInt(), // Green
        0xFF2196F3.toInt(), // Blue
        0xFF3F51B5.toInt(), // Indigo
        0xFF9C27B0.toInt(), // Purple
        0xFF795548.toInt(), // Brown
        0xFF607D8B.toInt(), // Blue Grey
        0xFF9E9E9E.toInt()  // Grey
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(androidx.compose.ui.graphics.Color(color), CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    .clickable { onColorSelected(color.toInt()) }
            )
        }
    }
}
@Composable
fun MasterPackageItem(
    masterPackage: MasterPackage,
    onEdit: () -> Unit,
    onExport: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (masterPackage.isPnd)
                MaterialTheme.colorScheme.errorContainer
            else if (masterPackage.isExported)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Name and type
                Column {
                    Text(
                        text = masterPackage.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Type: ${masterPackage.type}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Count: ${masterPackage.count}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Status and actions
                Row {
                    if (masterPackage.isPnd) {
                        Text(
                            text = "PND",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Master Package"
                        )
                    }

                    if (!masterPackage.isExported) {
                        IconButton(
                            onClick = { onExport(masterPackage.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Export Master Package"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Weight info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weight: ${masterPackage.weightKg} KG",
                    style = MaterialTheme.typography.bodyMedium
                )

                if (masterPackage.isPnd) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "PND Warning",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Missing weight data",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Shipments info
            if (masterPackage.shipments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Shipments: ${masterPackage.shipments.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { /* Show shipments details */ }
                    ) {
                        Text("View Details")
                    }
                }
            }

            // Export status indicator
            if (masterPackage.isExported) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Exported",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Exported",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
@Composable
fun AddMasterPackageDialog(
    groupId: String,
    onAddMP: (String, String, Int, String) -> Unit,
    onDismiss: () -> Unit
) {
    var mpName by remember { mutableStateOf("") }
    var mpCount by remember { mutableStateOf("1") }
    var mpType by remember { mutableStateOf("T") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Master Package") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // MP Name
                OutlinedTextField(
                    value = mpName,
                    onValueChange = { mpName = it },
                    label = { Text("Master Package Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // MP Count
                OutlinedTextField(
                    value = mpCount,
                    onValueChange = { mpCount = it },
                    label = { Text("Count") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // MP Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Type:")
                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = mpType == "T",
                            onClick = { mpType = "T" }
                        )
                        Text("T")

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = mpType == "B",
                            onClick = { mpType = "B" }
                        )
                        Text("B")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val count = mpCount.toIntOrNull() ?: 1
                    onAddMP(groupId, mpName, count, mpType)
                    onDismiss()
                },
                enabled = mpName.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditMasterPackageDialog(
    masterPackage: MasterPackage,
    onUpdateMP: (MasterPackage) -> Unit,
    onDismiss: () -> Unit
) {
    var mpName by remember { mutableStateOf(masterPackage.name) }
    var mpCount by remember { mutableStateOf(masterPackage.count.toString()) }
    var mpType by remember { mutableStateOf(masterPackage.type) }
    var mpWeight by remember { mutableStateOf(masterPackage.weightKg.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Master Package") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // MP Name
                OutlinedTextField(
                    value = mpName,
                    onValueChange = { mpName = it },
                    label = { Text("Master Package Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // MP Count
                OutlinedTextField(
                    value = mpCount,
                    onValueChange = { mpCount = it },
                    label = { Text("Count") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // MP Weight
                OutlinedTextField(
                    value = mpWeight,
                    onValueChange = { mpWeight = it },
                    label = { Text("Weight (KG)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // MP Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Type:")
                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = mpType == "T",
                            onClick = { mpType = "T" }
                        )
                        Text("T")

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = mpType == "B",
                            onClick = { mpType = "B" }
                        )
                        Text("B")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val count = mpCount.toIntOrNull() ?: masterPackage.count
                    val weight = mpWeight.toDoubleOrNull() ?: masterPackage.weightKg

                    val updatedMP = masterPackage.copy(
                        name = mpName,
                        count = count,
                        type = mpType,
                        weightKg = weight,
                        isPnd = weight <= 0.0
                    )

                    onUpdateMP(updatedMP)
                    onDismiss()
                },
                enabled = mpName.isNotBlank()
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
