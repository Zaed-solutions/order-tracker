package com.zaed.ordertracker.ui.settings

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.ui.components.AddMpGroupDialog
import com.zaed.ordertracker.ui.components.AddUserDialog
import com.zaed.ordertracker.ui.components.DeleteMpGroupConfirmationDialog
import com.zaed.ordertracker.ui.components.DeleteUserConfirmationDialog
import com.zaed.ordertracker.ui.components.EditMpGroupDialog
import com.zaed.ordertracker.ui.components.EditUserDialog
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme
import com.zaed.ordertracker.ui.util.GoogleAuthentication.signInOptions
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    task.addOnSuccessListener { account ->
                        Log.d("SettingsScreen", "onActivityResult: $account")
                        viewModel.getSignedInAccount()
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.google_login_error), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    SettingScreenContent(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                SettingsUiAction.DriveLogout -> {
                    viewModel.logout()
                }

                SettingsUiAction.OnDriveEmailClicked -> startForResult.launch(
                    GoogleSignIn.getClient(
                        context,
                        signInOptions
                    ).signInIntent
                )

                SettingsUiAction.OnBackClicked -> onNavigateBack()
                else -> viewModel.handleAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
                navigationIcon = {
                    IconButton(onClick = { onAction(SettingsUiAction.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Google Drive Email Field
            EditableTextField(
                label = "Google Drive",
                value = state.currentAccount?.email ?: "You Are Logged out",
                isEditing = state.isEditingDriveEmail,
                onEditClick = {
                    if (state.currentAccount == null) {
                        onAction(SettingsUiAction.OnDriveEmailClicked)
                    } else {
                        onAction(SettingsUiAction.DriveLogout)
                        onAction(SettingsUiAction.OnDriveEmailClicked)
                    }
                },
                onValueChange = { onAction(SettingsUiAction.OnDriveEmailChanged(it)) },
                onSubmit = { onAction(SettingsUiAction.OnDriveEmailSubmitted) },
                onCancel = { onAction(SettingsUiAction.OnDriveEmailEditCanceled) }
            )

            // Export Folder Field
            EditableTextField(
                label = "Export to Folder",
                value = state.exportFolderName,
                isEditing = state.isEditingExportFolder,
                onEditClick = { onAction(SettingsUiAction.OnExportFolderNameClicked) },
                onValueChange = { onAction(SettingsUiAction.OnExportFolderNameChanged(it)) },
                onSubmit = { onAction(SettingsUiAction.OnExportFolderNameSubmitted) },
                onCancel = { onAction(SettingsUiAction.OnExportFolderEditCanceled) }
            )

            // Firebase Password Field
            EditableTextField(
                label = "FireBase Password for email: ${state.firebaseEmail}",
                value = state.firebasePassword,
                isEditing = state.isEditingFirebasePassword,
                onEditClick = { onAction(SettingsUiAction.OnFirebasePasswordClicked) },
                onValueChange = { onAction(SettingsUiAction.OnFirebasePasswordChanged(it)) },
                onSubmit = { onAction(SettingsUiAction.OnFirebasePasswordSubmitted) },
                onCancel = { onAction(SettingsUiAction.OnFirebasePasswordEditCanceled) }
            )

            // Users Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
                    .wrapContentHeight(),
            ) {
                Text("Users")
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    LazyColumn {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.outline),
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

                                FilledIconButton(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    onClick = { onAction(SettingsUiAction.OnAddNewUserClicked) },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add User"
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
                                    onClick = { onAction(SettingsUiAction.OnEditUserClicked(user)) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .wrapContentSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit User"
                                    )
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
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete User"
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // MP Groups Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
                    .wrapContentHeight(),
            ) {
                Text("MP Groups Settings")
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    LazyColumn {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.outline),
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

                                FilledIconButton(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    onClick = { onAction(SettingsUiAction.OnAddNewMpGroupClicked) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add MP Group"
                                    )
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
                                    onClick = { onAction(SettingsUiAction.OnEditMpGroupClicked(group)) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit MP Group"
                                    )
                                }
                                if(group.canDelete) {
                                    FilledIconButton(
                                        onClick = { onAction(SettingsUiAction.OnRemoveMpGroup(group)) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentSize(),
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.error
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete MP Group"
                                        )
                                    }
                                }else{
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Display Dialogs
        if (state.isShowingAddUserDialog) {
            AddUserDialog(
                tempUsername = state.tempUsername,
                tempPassword = state.tempPassword,
                onUsernameChange = { onAction(SettingsUiAction.OnTempUsernameChanged(it)) },
                onPasswordChange = { onAction(SettingsUiAction.OnTempPasswordChanged(it)) },
                onConfirm = { onAction(SettingsUiAction.OnConfirmAddUser) },
                onDismiss = { onAction(SettingsUiAction.OnDismissUserDialog) }
            )
        }

        if (state.isShowingEditUserDialog && state.selectedUser != null) {
            EditUserDialog(
                tempUsername = state.tempUsername,
                tempPassword = state.tempPassword,
                onUsernameChange = { onAction(SettingsUiAction.OnTempUsernameChanged(it)) },
                onPasswordChange = { onAction(SettingsUiAction.OnTempPasswordChanged(it)) },
                onConfirm = { onAction(SettingsUiAction.OnConfirmEditUser) },
                onDismiss = { onAction(SettingsUiAction.OnDismissUserDialog) }
            )
        }

        if (state.isShowingDeleteUserDialog && state.selectedUser != null) {
            DeleteUserConfirmationDialog(
                user = state.selectedUser,
                onConfirm = { onAction(SettingsUiAction.OnConfirmDeleteUser) },
                onDismiss = { onAction(SettingsUiAction.OnDismissUserDialog) }
            )
        }

        if (state.isShowingAddMpGroupDialog) {
            AddMpGroupDialog(
                tempMpGroupName = state.tempMpGroupName,
                tempMpGroupColor = state.tempMpGroupColor,
                onMpGroupNameChange = { onAction(SettingsUiAction.OnTempMpGroupNameChanged(it)) },
                onMpGroupColorChange = { onAction(SettingsUiAction.OnTempMpGroupColorChanged(it)) },
                onConfirm = { onAction(SettingsUiAction.OnConfirmAddMpGroup) },
                onDismiss = { onAction(SettingsUiAction.OnDismissMpGroupDialog) }
            )
        }

        if (state.isShowingEditMpGroupDialog && state.selectedMpGroup != null) {
            EditMpGroupDialog(
                tempMpGroupName = state.tempMpGroupName,
                tempMpGroupColor = state.tempMpGroupColor,
                onMpGroupNameChange = { onAction(SettingsUiAction.OnTempMpGroupNameChanged(it)) },
                onMpGroupColorChange = { onAction(SettingsUiAction.OnTempMpGroupColorChanged(it)) },
                onConfirm = { onAction(SettingsUiAction.OnConfirmEditMpGroup) },
                onDismiss = { onAction(SettingsUiAction.OnDismissMpGroupDialog) },
                canEditName = state.selectedMpGroup.canEditName,
            )
        }

        if (state.isShowingDeleteMpGroupDialog && state.selectedMpGroup != null) {
            DeleteMpGroupConfirmationDialog(
                mpGroup = state.selectedMpGroup,
                onConfirm = { onAction(SettingsUiAction.OnConfirmDeleteMpGroup) },
                onDismiss = { onAction(SettingsUiAction.OnDismissMpGroupDialog) }
            )
        }
    }
}

@Composable
private fun EditableTextField(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    var tempValue by remember { mutableStateOf(value) }

    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = label,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
            value = if (isEditing) tempValue else value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            readOnly = !isEditing,
            onValueChange = {
                tempValue = it
                if (isEditing) {
                    onValueChange(it)
                }
            },
            enabled = isEditing,
            trailingIcon = {
                if (isEditing) {
                    Row {
                        IconButton(onClick = {
                            onSubmit()
                            tempValue = value
                        }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = {
                            onCancel()
                            tempValue = value
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                } else {
                    IconButton(onClick = {
                        onEditClick()
                        tempValue = value
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                            contentDescription = "Edit"
                        )
                    }
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
                exportFolderName = "MyExportFolder",
                firebaseEmail = "firebase@example.com",
                firebasePassword = "securePassword",
                isEditingDriveEmail = false,
                isEditingExportFolder = true,
                isEditingFirebasePassword = false,
                users = listOf(
                    User("user1", "password1", "hjkl"),
                    User("user2", "password2", "bnm,."),
                ),
                mpGroups = listOf(
                    MpGroup("5555555", "group1", 0xFF000000.toInt()),
                    MpGroup("6666666", "group2", 0xFF000000.toInt()),
                    MpGroup("7777777", "group3", 0xFF000000.toInt()),
                ),
                isShowingAddMpGroupDialog = false,
            ),
            {}
        )
    }
}