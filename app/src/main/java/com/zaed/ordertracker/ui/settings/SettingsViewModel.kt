package com.zaed.ordertracker.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.usecase.DeleteMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.DeleteUserUseCase
import com.zaed.ordertracker.domain.usecase.GetAllUsersUseCase
import com.zaed.ordertracker.domain.usecase.GetExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.GetFirebaseCredentialUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.GetSignedInAccountUseCase
import com.zaed.ordertracker.domain.usecase.LogOutUseCase
import com.zaed.ordertracker.domain.usecase.SaveExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSignedInAccountUseCase: GetSignedInAccountUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getMpGroupsUseCase: GetMpGroupsUseCase,
    private val saveExportFolderNameUseCase: SaveExportFolderNameUseCase,
    private val getExportFolderNameUseCase: GetExportFolderNameUseCase,
    private val getFirebaseCredentialUseCase: GetFirebaseCredentialUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val saveMpGroupUseCase: SaveMpGroupUseCase,
    private val deleteMpGroupUseCase: DeleteMpGroupUseCase,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        getSignedInAccount()
        loadUsers()
        loadMpGroups()
        loadFirebaseCredential()
        loadExportFolderName()
    }

    private fun loadExportFolderName() {
        viewModelScope.launch(Dispatchers.IO) {
            getExportFolderNameUseCase().let { folderName ->
                _uiState.update { it.copy(exportFolderName = folderName) }
                Log.d("SettingsViewModel", "loadExportFolderName: $folderName")
            }
        }

    }

    private fun loadFirebaseCredential() {
        viewModelScope.launch(Dispatchers.IO) {
            getFirebaseCredentialUseCase().let { result ->
                result.onSuccess { credential ->
                    _uiState.update {
                        it.copy(
                            firebasePassword = credential.password,
                            firebaseEmail = credential.email
                        )
                    }
                    Log.d("SettingsViewModel", "loadFirebaseCredential: $credential")
                }.onFailure {
                    Log.d("SettingsViewModel", "loadFirebaseCredential: $it")
                }
            }
        }
    }

    fun getSignedInAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            getSignedInAccountUseCase().let { result ->
                result.onSuccess { account ->
                    Log.d("SettingsViewModel", "getSignedInAccount: $account")
                    _uiState.value = _uiState.value.copy(
                        currentAccount = account
                    )
                }.onFailure {
                    Log.d("SettingsViewModel", "getSignedInAccount: $it")
                }
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllUsersUseCase().collect { result ->
                result.onSuccess { users ->
                    _uiState.update { it.copy(users = users) }
                }.onFailure {
                    Log.d("SettingsViewModel", "loadUsers: $it")
                }
            }
        }
    }

    private fun loadMpGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            getMpGroupsUseCase().collect { result ->
                result.onSuccess { groups ->
                    _uiState.update { it.copy(mpGroups = groups) }
                    Log.d("SettingsViewModel", "loadMpGroups: $groups")
                }.onFailure {
                    Log.d("SettingsViewModel", "loadMpGroups: $it")
                }
            }
        }
    }

    fun handleAction(action: SettingsUiAction) {
        when (action) {
            // Drive Email actions
            is SettingsUiAction.OnDriveEmailClicked -> {
                _uiState.update { it.copy(isEditingDriveEmail = true) }
            }

            // Export Folder actions
            is SettingsUiAction.OnExportFolderNameClicked -> {
                _uiState.update { it.copy(isEditingExportFolder = true) }
            }

            is SettingsUiAction.OnExportFolderNameChanged -> {
                _uiState.update { it.copy(exportFolderName = action.value) }
            }

            is SettingsUiAction.OnExportFolderNameSubmitted -> {
                viewModelScope.launch {
                    saveExportFolderNameUseCase(_uiState.value.exportFolderName)
                    _uiState.update { it.copy(isEditingExportFolder = false) }
                }
            }

            is SettingsUiAction.OnExportFolderEditCanceled -> {
                viewModelScope.launch {
                    val originalFolder = getExportFolderNameUseCase()
                    _uiState.update {
                        it.copy(
                            exportFolderName = originalFolder,
                            isEditingExportFolder = false
                        )
                    }
                }
            }

            // Firebase Password actions
            is SettingsUiAction.OnFirebasePasswordClicked -> {
                _uiState.update { it.copy(isEditingFirebasePassword = true) }
            }

            is SettingsUiAction.OnFirebasePasswordChanged -> {
                _uiState.update { it.copy(firebasePassword = action.value) }
            }

            is SettingsUiAction.OnFirebasePasswordEditCanceled -> {
                viewModelScope.launch {
                    val originalPassword = getFirebaseCredentialUseCase().getOrNull()?.password
                    _uiState.update {
                        it.copy(
                            firebasePassword = originalPassword ?: "",
                            isEditingFirebasePassword = false
                        )
                    }
                }
            }

            // User management actions
            is SettingsUiAction.OnAddNewUserClicked -> {
                _uiState.update {
                    it.copy(
                        isShowingAddUserDialog = true,
                        tempUsername = "",
                        tempPassword = ""
                    )
                }
            }

            is SettingsUiAction.OnEditUserClicked -> {
                _uiState.update {
                    it.copy(
                        selectedUser = action.user,
                        isShowingEditUserDialog = true,
                        tempUsername = action.user.username,
                        tempPassword = action.user.password
                    )
                }
            }

            is SettingsUiAction.OnRemoveUserClicked -> {
                _uiState.update {
                    it.copy(
                        selectedUser = action.user,
                        isShowingDeleteUserDialog = true
                    )
                }
            }

            is SettingsUiAction.OnTempUsernameChanged -> {
                _uiState.update { it.copy(tempUsername = action.value) }
            }

            is SettingsUiAction.OnTempPasswordChanged -> {
                _uiState.update { it.copy(tempPassword = action.value) }
            }

            is SettingsUiAction.OnConfirmAddUser -> {
                viewModelScope.launch {
                    // Generate a random ID for the new user
                    val newUser = User(
                        id = java.util.UUID.randomUUID().toString(),
                        username = _uiState.value.tempUsername,
                        password = _uiState.value.tempPassword
                    )
                    saveUserUseCase(newUser)
                    _uiState.update {
                        it.copy(
                            isShowingAddUserDialog = false,
                            tempUsername = "",
                            tempPassword = ""
                        )
                    }
                }
            }

            is SettingsUiAction.OnConfirmEditUser -> {
                viewModelScope.launch {
                    val currentUser = _uiState.value.selectedUser
                    if (currentUser != null) {
                        val updatedUser = currentUser.copy(
                            username = _uiState.value.tempUsername,
                            password = _uiState.value.tempPassword
                        )
                        saveUserUseCase(updatedUser)
                        _uiState.update {
                            it.copy(
                                isShowingEditUserDialog = false,
                                selectedUser = null,
                                tempUsername = "",
                                tempPassword = ""
                            )
                        }
                    }
                }
            }

            is SettingsUiAction.OnConfirmDeleteUser -> {
                viewModelScope.launch {
                    val userToDelete = _uiState.value.selectedUser
                    if (userToDelete != null) {
                        deleteUserUseCase(userToDelete.id)
                        // Refresh user list
                        _uiState.update {
                            it.copy(
                                isShowingDeleteUserDialog = false,
                                selectedUser = null
                            )
                        }
                    }
                }
            }

            is SettingsUiAction.OnDismissUserDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingAddUserDialog = false,
                        isShowingEditUserDialog = false,
                        isShowingDeleteUserDialog = false,
                        selectedUser = null,
                        tempUsername = "",
                        tempPassword = ""
                    )
                }
            }

            // MP Group management actions
            is SettingsUiAction.OnAddNewMpGroupClicked -> {
                _uiState.update {
                    it.copy(
                        isShowingAddMpGroupDialog = true,
                        tempMpGroupName = "",
                        tempMpGroupColor = 0xFF000000.toInt()
                    )
                }
            }

            is SettingsUiAction.OnEditMpGroupClicked -> {
                Log.d("SettingsViewModel", "OnEditMpGroupClicked: ${action.group}")
                _uiState.update {
                    it.copy(
                        selectedMpGroup = action.group,
                        isShowingEditMpGroupDialog = true,
                        tempMpGroupName = action.group.name,
                        tempMpGroupColor = action.group.color
                    )
                }
            }

            is SettingsUiAction.OnRemoveMpGroup -> {
                _uiState.update {
                    it.copy(
                        selectedMpGroup = action.group,
                        isShowingDeleteMpGroupDialog = true
                    )
                }
            }

            is SettingsUiAction.OnTempMpGroupNameChanged -> {
                _uiState.update { it.copy(tempMpGroupName = action.value) }
            }

            is SettingsUiAction.OnTempMpGroupColorChanged -> {
                _uiState.update { it.copy(tempMpGroupColor = action.value) }
            }

            is SettingsUiAction.OnConfirmAddMpGroup -> {
                viewModelScope.launch {
                    // Generate a random ID for the new MP group
                    val newMpGroup = MpGroup(

                        name = _uiState.value.tempMpGroupName,
                        color = _uiState.value.tempMpGroupColor
                    )
                    saveMpGroupUseCase(newMpGroup)
                    _uiState.update {
                        it.copy(
                            isShowingAddMpGroupDialog = false,
                            tempMpGroupName = "",
                            tempMpGroupColor = 0xFF000000.toInt()
                        )
                    }
                }
            }

            is SettingsUiAction.OnConfirmEditMpGroup -> {
                viewModelScope.launch {
                    val currentGroup = _uiState.value.selectedMpGroup
                    if (currentGroup != null) {
                        val updatedGroup = currentGroup.copy(
                            name = _uiState.value.tempMpGroupName,
                            color = _uiState.value.tempMpGroupColor
                        )
                        Log.d("SettingsViewModel", "OnConfirmEditMpGroup: $updatedGroup")
                        saveMpGroupUseCase(updatedGroup)
                        _uiState.update {
                            it.copy(
                                isShowingEditMpGroupDialog = false,
                                selectedMpGroup = null,
                                tempMpGroupName = "",
                                tempMpGroupColor = 0xFF000000.toInt()
                            )
                        }
                    }
                }
            }

            is SettingsUiAction.OnConfirmDeleteMpGroup -> {
                viewModelScope.launch {
                    val groupToDelete = _uiState.value.selectedMpGroup
                    if (groupToDelete != null) {
                        deleteMpGroupUseCase(groupToDelete.id)
                        _uiState.update {
                            it.copy(
                                isShowingDeleteMpGroupDialog = false,
                                selectedMpGroup = null
                            )
                        }
                    }
                }
            }

            is SettingsUiAction.OnDismissMpGroupDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingAddMpGroupDialog = false,
                        isShowingEditMpGroupDialog = false,
                        isShowingDeleteMpGroupDialog = false,
                        selectedMpGroup = null,
                        tempMpGroupName = "",
                        tempMpGroupColor = 0xFF000000.toInt()
                    )
                }
            }

            else -> {}
        }
    }

    fun logout() {
        viewModelScope.launch {
            logOutUseCase()
            _uiState.update { it.copy(currentAccount = null) }
        }
    }
}