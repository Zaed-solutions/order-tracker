package com.zaed.ordertracker.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SettingScreenContent(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                else -> viewModel.handleAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    modifier: Modifier,
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = { SettingsUiAction.OnBackClicked }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReadOnlyTextField(
                label = "Google Drive",
                value = state.driveEmail,
                onClick = { onAction(SettingsUiAction.OnDriveEmailClicked) }
            )
            ReadOnlyTextField(
                label = "Export to Folder",
                value = state.exportFolderName,
                onClick = { onAction(SettingsUiAction.OnExportFolderNameClicked) }
            )
            ReadOnlyTextField(
                label = "FireBase Password for email: ${state.firebaseEmail}",
                value = state.driveEmail,
                onClick = { onAction(SettingsUiAction.OnFirebasePasswordClicked) }
            )
            Text("Users")
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "UserName",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Password",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Edit",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Remove",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            FilledIconButton(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                onClick = { onAction(SettingsUiAction.OnAddNewUserClicked) },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    null
                                )
                            }
                        }
                    }
                    items(state.users) { user ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                user.username,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                user.password,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            FilledIconButton(
                                onClick = { onAction(SettingsUiAction.OnEditUserClicked) },
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentSize()
                            ) {
                                Icon(imageVector = Icons.Default.Edit, null)
                            }
                            FilledIconButton(
                                onClick = { onAction(SettingsUiAction.OnRemoveUserClicked(user)) },
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentSize(),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(imageVector = Icons.Default.Delete, null)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            Text("MP Groups Settings")
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                "Mp Group",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Color",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Edit",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Remove",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            FilledIconButton(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                onClick = { onAction(SettingsUiAction.OnAddNewMpGroupClicked) }
                            ) {
                                Icon(imageVector = Icons.Default.Add, null)
                            }
                        }
                    }
                    items(state.mpGroups) { group ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                group.name,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(16.dp)
                                    .background(Color(group.color), CircleShape)
                            )
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentSize(),
                                onClick = { onAction(SettingsUiAction.OnEditMpGroupClicked) }
                            ) {
                                Icon(imageVector = Icons.Default.Edit, null)
                            }
                            FilledIconButton(
                                onClick = { onAction(SettingsUiAction.OnRemoveMpGroup(group)) },
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentSize(),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(imageVector = Icons.Default.Delete, null)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun ReadOnlyTextField(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column() {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = label,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            readOnly = true,
            onValueChange = {},
            enabled = false,
            trailingIcon = {
                IconButton(
                    onClick = onClick
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Default.ArrowForwardIos, null)
                }
            }
        )
    }
}


@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
private fun SettingPreview() {
    ProjectTemplateTheme {
        SettingScreenContent(
            modifier = Modifier,
            state = SettingsUiState(
                driveEmail = "driveEmail",
                exportFolderName = "exportFolderName",
                firebaseEmail = "firebaseEmail",
                users = listOf(
                    User("user1", "password1", "hjkl"),
                    User("user2", "password2", "bnm,."),
                ),
                mpGroups = listOf(
                    MpGroup("5555555", "group1", 0xFF000000.toInt()),
                    MpGroup("6666666", "group2", 0xFF000000.toInt()),
                    MpGroup("7777777", "group3", 0xFF000000.toInt()),
                )
            ),
            {}
        )
    }
}