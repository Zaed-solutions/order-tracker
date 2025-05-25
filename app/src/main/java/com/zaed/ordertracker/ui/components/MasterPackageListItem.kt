package com.zaed.ordertracker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.WebStories
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.WebStories
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MasterPackageType
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MasterPackageScreenContent(
    modifier: Modifier = Modifier,
    masterPackages: List<MasterPackage>,
    masterPackageGroup: List<MpGroup>,
    onAddNewMasterPackage: (MasterPackage) -> Unit = {},
    onEditMasterPackage: (MasterPackage) -> Unit = {},
    onDeleteMasterPackage: (MasterPackage) -> Unit = {},
    onMasterPackageClicked: (MasterPackage) -> Unit = {},
    onMasterPackageGroupClicked: (MpGroup) -> Unit = {},
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    var selectedMasterPackage by remember { mutableStateOf(MasterPackage()) }
    var selectedMasterPackageGroup by remember { mutableStateOf(MpGroup()) }
    var isEditMasterPackageBottomSheetVisible by remember { mutableStateOf(false) }
    var isEditMasterPackageGroupBottomSheetVisible by remember { mutableStateOf(false) }

    var isSaveMasterPackageBottomSheetVisible by remember { mutableStateOf(false) }
    var isDeleteMasterPackageBottomSheetVisible by remember { mutableStateOf(false) }
    var isDeleteMasterPackageGroupBottomSheetVisible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            stickyHeader {
                if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    CompactMasterPackageListHeader()
                    HorizontalDivider()
                } else {
                    MasterPackageListHeader()
                    HorizontalDivider()
                }
            }
            items(masterPackageGroup) { group ->
                if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    CompactMasterPackageGroupListItem(
                        masterPackageGroup = group,
                        onMasterPackageGroupClicked = onMasterPackageGroupClicked,
                    )
                } else {
                    MasterPackageGroupListItem(
                        masterPackageGroup = group,
                        onEditMasterPackageGroup = {
                            selectedMasterPackageGroup = it
                            isEditMasterPackageGroupBottomSheetVisible = true
                        },
                        onDeleteMasterPackageGroup = {
                            selectedMasterPackageGroup = it
                            isDeleteMasterPackageGroupBottomSheetVisible = true
                        },
                        onMasterPackageGroupClicked = onMasterPackageGroupClicked,
                    )
                    HorizontalDivider(Modifier.padding(top = 8.dp))
                }
            }

            items(masterPackages) { mp ->
                if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    CompactMasterPackageListItem(
                        masterPackage = mp,
                        onEditMasterPackage = {
                            selectedMasterPackage = it
                            isEditMasterPackageBottomSheetVisible = true
                        },
                        onDeleteMasterPackage = {
                            selectedMasterPackage = it
                            isDeleteMasterPackageBottomSheetVisible = true
                        },
                        onMasterPackageClicked = onMasterPackageClicked,
                    )
                } else {
                    MasterPackageListItem(
                        masterPackage = mp,
                        onEditMasterPackage = {
                            selectedMasterPackage = it
                            isEditMasterPackageBottomSheetVisible = true
                        },
                        onDeleteMasterPackage = {
                            selectedMasterPackage = it
                            isDeleteMasterPackageBottomSheetVisible = true
                        },
                        onMasterPackageClicked = onMasterPackageClicked,
                    )
                    HorizontalDivider(Modifier.padding(top = 8.dp))
                }
            }
        }
        Button(
            onClick = {
                isSaveMasterPackageBottomSheetVisible = true
            },
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add New MP",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        AnimatedVisibility(isSaveMasterPackageBottomSheetVisible) {
            SaveMasterPackageBottomSheet(
                onDismiss = { isSaveMasterPackageBottomSheetVisible = false },
                onSaveMasterPackage = {
                    isSaveMasterPackageBottomSheetVisible = false
                    onAddNewMasterPackage(it)
                },
            )
        }
        AnimatedVisibility(isEditMasterPackageBottomSheetVisible) {
            EditMasterPackageBottomSheet(
                initialMasterPackage = selectedMasterPackage,
                onDismiss = { isEditMasterPackageBottomSheetVisible = false },
                onEditMasterPackage = {
                    isEditMasterPackageBottomSheetVisible = false
                    onEditMasterPackage(it)
                },
            )
        }
        ConfirmDeleteBottomSheet(
            visible = isDeleteMasterPackageBottomSheetVisible,
            label = selectedMasterPackage.name,
            onDismiss = { isDeleteMasterPackageBottomSheetVisible = false },
            onConfirm = {
                onDeleteMasterPackage(selectedMasterPackage)
                isDeleteMasterPackageBottomSheetVisible = false
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveMasterPackageBottomSheet(
    onDismiss: () -> Unit,
    onSaveMasterPackage: (MasterPackage) -> Unit,
) {
    var selectedMasterPackage by remember { mutableStateOf(MasterPackage()) }
    var nameError by remember { mutableStateOf(false) }
    var countError by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            Surface(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(modifier = Modifier.size(width = 32.dp, height = 4.dp))
            }
        },
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Create Master Package",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = selectedMasterPackage.name,
                    onValueChange = {
                        selectedMasterPackage = selectedMasterPackage.copy(name = it)
                        nameError = false
                    },
                    label = { Text("Package Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.BusinessCenter,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    isError = nameError,
                    supportingText =
                        if (nameError) {
                            { Text("Package name is required") }
                        } else {
                            null
                        },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        ),
                )
            }

            item {
                OutlinedTextField(
                    value = selectedMasterPackage.count.toString(),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { count ->
                            selectedMasterPackage = selectedMasterPackage.copy(count = count)
                            countError = false
                        } ?: run { countError = true }
                    },
                    label = { Text("Package Count") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Numbers,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    isError = countError,
                    supportingText =
                        if (countError) {
                            { Text("Please enter a valid number") }
                        } else {
                            null
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        ),
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "Package Type",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = if (selectedMasterPackage.type == MasterPackageType.T) "Type T" else "Type B",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Switch(
                            checked = selectedMasterPackage.type == MasterPackageType.T,
                            onCheckedChange = {
                                selectedMasterPackage =
                                    selectedMasterPackage.copy(
                                        type = if (it) MasterPackageType.T else MasterPackageType.B,
                                    )
                            },
                            colors =
                                SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border =
                            BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            ),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Button(
                        onClick = {
                            when {
                                selectedMasterPackage.name.isEmpty() -> nameError = true
                                else -> onSaveMasterPackage(selectedMasterPackage)
                            }
                        },
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Package",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMasterPackageBottomSheet(
    initialMasterPackage: MasterPackage,
    onDismiss: () -> Unit,
    onEditMasterPackage: (MasterPackage) -> Unit,
) {
    var selectedMasterPackage by remember { mutableStateOf(initialMasterPackage) }
    var nameError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            Surface(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(modifier = Modifier.size(width = 32.dp, height = 4.dp))
            }
        },
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Edit Master Package",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = selectedMasterPackage.name,
                    onValueChange = {
                        selectedMasterPackage = selectedMasterPackage.copy(name = it)
                        nameError = false
                    },
                    label = { Text("Package Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.BusinessCenter,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    isError = nameError,
                    supportingText =
                        if (nameError) {
                            { Text("Package name is required") }
                        } else {
                            null
                        },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        ),
                )
            }

            item {
                OutlinedTextField(
                    value = selectedMasterPackage.weightKg.toString(),
                    onValueChange = { value ->
                        value.toDoubleOrNull()?.let { weight ->
                            selectedMasterPackage = selectedMasterPackage.copy(weightKg = weight)
                            weightError = false
                        } ?: run { weightError = true }
                    },
                    label = { Text("Weight (KG)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Scale,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    trailingIcon = {
                        Text(
                            text = "kg",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                    },
                    isError = weightError,
                    supportingText =
                        if (weightError) {
                            { Text("Please enter a valid weight") }
                        } else {
                            null
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        ),
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "Package Type",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = if (selectedMasterPackage.type == MasterPackageType.T) "Type T" else "Type B",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Switch(
                            checked = selectedMasterPackage.type == MasterPackageType.T,
                            onCheckedChange = {
                                selectedMasterPackage =
                                    selectedMasterPackage.copy(
                                        type = if (it) MasterPackageType.T else MasterPackageType.B,
                                    )
                            },
                            colors =
                                SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border =
                            BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            ),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Button(
                        onClick = {
                            when {
                                selectedMasterPackage.name.isEmpty() -> nameError = true
                                else -> onEditMasterPackage(selectedMasterPackage)
                            }
                        },
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Update Package",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MasterPackageListHeader(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Status icon column
            Spacer(Modifier.weight(1f))

            // Name column
            Text(
                text = "MP",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // KG column
            Text(
                text = "KG",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // PCS column
            Text(
                text = "PCS",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // MP KG column
            Text(
                text = "MP KG",
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // Type column
            Text(
                text = "T/B",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // Status column (for PND)
            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            // More Option
            Text(
                text = "More",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
        }
    }
}

@Composable
fun MasterPackageListItem(
    modifier: Modifier = Modifier,
    masterPackage: MasterPackage,
    onEditMasterPackage: (MasterPackage) -> Unit = {},
    onDeleteMasterPackage: (MasterPackage) -> Unit = {},
    onMasterPackageClicked: (MasterPackage) -> Unit = {},
) {
    Surface(
        onClick = { onMasterPackageClicked(masterPackage) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(top = 8.dp),
        ) {
            // STATUS ICON
            Icon(
                imageVector = if (masterPackage.exported) Icons.Filled.Circle else Icons.Outlined.Circle,
                contentDescription = "MP Status",
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )

            // MP ICON
            Surface(
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentSize()
                        .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
            ) {
                Text(
                    text = masterPackage.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
            }
            // KG
            Text(
                text = masterPackage.shipments.sumOf { it.weight }.toString(),
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            // PCS
            Text(
                text = masterPackage.count.toString(),
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            // MP KG
            Text(
                text = masterPackage.weightKg.toString(),
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            // T/B
            Surface(
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentSize()
                        .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color =
                    when (masterPackage.type) {
                        MasterPackageType.T -> Color.Blue
                        else -> Color.Yellow
                    },
            ) {
                Text(
                    text = masterPackage.type.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color =
                        when (masterPackage.type) {
                            MasterPackageType.T -> Color.White
                            else -> Color.Black
                        },
                )
            }
            // PENDING STATUS
            if (masterPackage.isPnd) {
                Surface(
                    modifier =
                        Modifier
                            .weight(1f)
                            .wrapContentSize()
                            .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Red,
                ) {
                    Text(
                        text = "PND",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            } else {
                Spacer(Modifier.weight(1f))
            }
            MoreDropDownMenu(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                items =
                    listOf(
                        MoreDropdownItem(
                            title = stringResource(R.string.edit),
                            icon = Icons.Default.Edit,
                            onClick = { onEditMasterPackage(masterPackage) },
                            tint = MaterialTheme.colorScheme.primary,
                        ),
                        MoreDropdownItem(
                            title = stringResource(R.string.delete),
                            icon = Icons.Default.DeleteOutline,
                            onClick = { onDeleteMasterPackage(masterPackage) },
                            tint = MaterialTheme.colorScheme.error,
                        ),
                    ),
            )
        }
    }
}

@Composable
fun MasterPackageGroupListItem(
    modifier: Modifier = Modifier,
    masterPackageGroup: MpGroup,
    onEditMasterPackageGroup: (MpGroup) -> Unit = {},
    onDeleteMasterPackageGroup: (MpGroup) -> Unit = {},
    onMasterPackageGroupClicked: (MpGroup) -> Unit = {},
) {
    val pndCount = remember { masterPackageGroup.masterPackages.count { it.isPnd } }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .padding(top = 8.dp)
                .clickable { onMasterPackageGroupClicked(masterPackageGroup) },
    ) {
        // STATUS ICON
        Icon(
            imageVector = if (masterPackageGroup.isExported) Icons.Default.WebStories else Icons.Outlined.WebStories,
            contentDescription = "MP Status",
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        // MP ICON
        Surface(
            modifier =
                Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(masterPackageGroup.color),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Text(
                text = masterPackageGroup.name,
                textAlign = TextAlign.Center,
                color = contentColorFor(Color(masterPackageGroup.color)),
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
        // KG
        Text(
            text =
                masterPackageGroup.masterPackages
                    .sumOf { it.shipments.sumOf { it.weight } }
                    .toString(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        // PCS
        Text(
            text = masterPackageGroup.masterPackages.sumOf { it.count }.toString(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        // MP KG
        Text(
            text = "-",
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        // T/B
        Surface(
            modifier =
                Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        ) {
            Text(
                text = masterPackageGroup.name + masterPackageGroup.masterPackages.size,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
        // PENDING STATUS
        if (pndCount > 0) {
            Surface(
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentSize()
                        .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.Red,
            ) {
                Text(
                    text = "$pndCount PND",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        MoreDropDownMenu(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            items =
                listOf(
                    MoreDropdownItem(
                        title = stringResource(R.string.edit),
                        icon = Icons.Default.Edit,
                        onClick = { onEditMasterPackageGroup(masterPackageGroup) },
                        tint = MaterialTheme.colorScheme.primary,
                    ),
                    MoreDropdownItem(
                        title = stringResource(R.string.delete),
                        icon = Icons.Default.DeleteOutline,
                        onClick = { onDeleteMasterPackageGroup(masterPackageGroup) },
                        tint = MaterialTheme.colorScheme.error,
                    ),
                ),
        )
    }
}

@Composable
fun CompactMasterPackageListItem(
    modifier: Modifier = Modifier,
    masterPackage: MasterPackage,
    onEditMasterPackage: (MasterPackage) -> Unit = {},
    onDeleteMasterPackage: (MasterPackage) -> Unit = {},
    onMasterPackageClicked: (MasterPackage) -> Unit = {},
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onMasterPackageClicked(masterPackage) },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = if (masterPackage.exported) Icons.Filled.Circle else Icons.Outlined.Circle,
                        contentDescription = "Status",
                        tint = if (masterPackage.exported) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(12.dp),
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Text(
                            text = masterPackage.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color =
                            when (masterPackage.type) {
                                MasterPackageType.T -> Color.Blue
                                else -> Color.Yellow
                            },
                    ) {
                        Text(
                            text = masterPackage.type.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color =
                                when (masterPackage.type) {
                                    MasterPackageType.T -> Color.White
                                    else -> Color.Black
                                },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        )
                    }

                    if (masterPackage.isPnd) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.Red,
                        ) {
                            Text(
                                text = "PND",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            )
                        }
                    }

                    MoreDropDownMenu(
                        items =
                            listOf(
                                MoreDropdownItem(
                                    title = stringResource(R.string.edit),
                                    icon = Icons.Default.Edit,
                                    onClick = { onEditMasterPackage(masterPackage) },
                                    tint = MaterialTheme.colorScheme.primary,
                                ),
                                MoreDropdownItem(
                                    title = stringResource(R.string.delete),
                                    icon = Icons.Default.DeleteOutline,
                                    onClick = { onDeleteMasterPackage(masterPackage) },
                                    tint = MaterialTheme.colorScheme.error,
                                ),
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CompactInfoItem(
                    label = "Weight",
                    value = "${masterPackage.shipments.sumOf { it.weight }} kg",
                    icon = Icons.Default.Scale,
                    color = MaterialTheme.colorScheme.secondary,
                )

                CompactInfoItem(
                    label = "Count",
                    value = "${masterPackage.count} pcs",
                    icon = Icons.Default.Numbers,
                    color = MaterialTheme.colorScheme.tertiary,
                )

                CompactInfoItem(
                    label = "MP Weight",
                    value = "${masterPackage.weightKg} kg",
                    icon = Icons.Default.BusinessCenter,
                    color = Color.Red,
                )
            }
        }
    }
}

@Composable
fun CompactMasterPackageGroupListItem(
    modifier: Modifier = Modifier,
    masterPackageGroup: MpGroup,
    onMasterPackageGroupClicked: (MpGroup) -> Unit = {},
) {
    val pndCount = remember { masterPackageGroup.masterPackages.count { it.isPnd } }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clickable { onMasterPackageGroupClicked(masterPackageGroup) },
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = if (masterPackageGroup.isExported) Icons.Default.WebStories else Icons.Outlined.WebStories,
                        contentDescription = "Group Status",
                        tint = if (masterPackageGroup.isExported) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp),
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(masterPackageGroup.color),
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Text(
                            text = masterPackageGroup.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = contentColorFor(Color(masterPackageGroup.color)),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                    ) {
                        Text(
                            text = "${masterPackageGroup.masterPackages.size} MPs",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }

                    if (pndCount > 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Red,
                        ) {
                            Text(
                                text = "$pndCount PND",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CompactInfoItem(
                    label = "Total Weight",
                    value = "${masterPackageGroup.masterPackages.sumOf { it.shipments.sumOf { it.weight } }} kg",
                    icon = Icons.Default.Scale,
                    color = MaterialTheme.colorScheme.secondary,
                )

                CompactInfoItem(
                    label = "Total Count",
                    value = "${masterPackageGroup.masterPackages.sumOf { it.count }} pcs",
                    icon = Icons.Default.Numbers,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            if (masterPackageGroup.masterPackages.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                ) {
                    items(masterPackageGroup.masterPackages.take(5)) { mp ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                            border =
                                BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            ) {
                                Icon(
                                    imageVector = if (mp.exported) Icons.Filled.Circle else Icons.Outlined.Circle,
                                    contentDescription = null,
                                    tint = if (mp.exported) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(8.dp),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = mp.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }

                    if (masterPackageGroup.masterPackages.size > 5) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                            ) {
                                Text(
                                    text = "+${masterPackageGroup.masterPackages.size - 5}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactInfoItem(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = color.copy(alpha = 0.1f),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .size(16.dp),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CompactMasterPackageListHeader(modifier: Modifier = Modifier) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            // Top row - Status, Name, Type, PND Status, More
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "●", // Status indicator placeholder
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.size(12.dp),
                    )

                    Text(
                        text = "NAME",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "TYPE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "PND",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "MORE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom row - Weight metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = "WEIGHT",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )

                Text(
                    text = "COUNT",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )

                Text(
                    text = "MP WEIGHT",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun MpItemPreview() {
    ProjectTemplateTheme {
        MasterPackageScreenContent(
            masterPackages =
                listOf(
                    MasterPackage(
                        id = "1",
                        name = "Group1",
                        count = 10,
                        type = MasterPackageType.T,
                        weightKg = 50.5,
                    ),
                    MasterPackage(
                        id = "2",
                        name = "Group2",
                        count = 20,
                        type = MasterPackageType.T,
                        weightKg = 60.5,
                    ),
                ),
            masterPackageGroup =
                listOf(
                    MpGroup(
                        id = "1",
                        isExported = true,
                        name = "Group1",
                        color = 0xFF2196F3.toInt(),
                        masterPackages =
                            listOf(
                                MasterPackage(
                                    id = "1",
                                    name = "MP1",
                                    count = 10,
                                    type = MasterPackageType.T,
                                    exported = true,
                                ),
                                MasterPackage(
                                    id = "2",
                                    name = "MP2",
                                    count = 20,
                                    type = MasterPackageType.T,
                                ),
                            ),
                    ),
                ),
            windowWidthSizeClass = WindowWidthSizeClass.MEDIUM,
        )
    }
}
