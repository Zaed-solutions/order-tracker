package com.zaed.ordertracker.ui.settings

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User

sealed class SettingsUiAction {
    data object OnBackClicked : SettingsUiAction()

    // Drive Email actions
    data object OnDriveEmailClicked : SettingsUiAction()
    data object DriveLogout: SettingsUiAction()
    data class OnDriveEmailChanged(val value: String) : SettingsUiAction()
    data object OnDriveEmailSubmitted : SettingsUiAction()
    data object OnDriveEmailEditCanceled : SettingsUiAction()

    // Export Folder actions
    data object OnExportFolderNameClicked : SettingsUiAction()
    data class OnExportFolderNameChanged(val value: String) : SettingsUiAction()
    data object OnExportFolderNameSubmitted : SettingsUiAction()
    data object OnExportFolderEditCanceled : SettingsUiAction()

    // Firebase Password actions
    data object OnFirebasePasswordClicked : SettingsUiAction()
    data class OnFirebasePasswordChanged(val value: String) : SettingsUiAction()
    data object OnFirebasePasswordSubmitted : SettingsUiAction()
    data object OnFirebasePasswordEditCanceled : SettingsUiAction()

    // User management actions
    data object OnAddNewUserClicked : SettingsUiAction()
    data class OnEditUserClicked(val user: User) : SettingsUiAction()
    data class OnRemoveUserClicked(val user: User) : SettingsUiAction()
    data object OnConfirmAddUser : SettingsUiAction()
    data object OnConfirmEditUser : SettingsUiAction()
    data object OnConfirmDeleteUser : SettingsUiAction()
    data object OnDismissUserDialog : SettingsUiAction()
    data class OnTempUsernameChanged(val value: String) : SettingsUiAction()
    data class OnTempPasswordChanged(val value: String) : SettingsUiAction()

    // MP Group management actions
    data object OnAddNewMpGroupClicked : SettingsUiAction()
    data class OnEditMpGroupClicked(val group: MpGroup) : SettingsUiAction()
    data class OnRemoveMpGroup(val group: MpGroup) : SettingsUiAction()
    data object OnConfirmAddMpGroup : SettingsUiAction()
    data object OnConfirmEditMpGroup : SettingsUiAction()
    data object OnConfirmDeleteMpGroup : SettingsUiAction()
    data object OnDismissMpGroupDialog : SettingsUiAction()
    data class OnTempMpGroupNameChanged(val value: String) : SettingsUiAction()
    data class OnTempMpGroupColorChanged(val value: Int) : SettingsUiAction()
}
