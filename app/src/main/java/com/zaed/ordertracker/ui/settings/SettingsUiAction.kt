package com.zaed.ordertracker.ui.settings

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User

sealed interface SettingsUiAction {
    data object OnBackClicked : SettingsUiAction
    data object OnDriveEmailClicked : SettingsUiAction
    data object OnExportFolderNameClicked : SettingsUiAction
    data class OnExportFolderNameUpdated(val newName :String) : SettingsUiAction
    data object OnFirebasePasswordClicked : SettingsUiAction
    data class OnFirebasePasswordUpdated(val newPassword :String) : SettingsUiAction
    data object OnAddNewUserClicked : SettingsUiAction
    data class OnAddNewUser(val newUser :User) : SettingsUiAction
    data class OnRemoveUserClicked(val user :User) : SettingsUiAction
    data class OnRemoveUser(val user :User) : SettingsUiAction
    data object OnEditUserClicked : SettingsUiAction
    data class OnEditUser(val user :User) : SettingsUiAction
    data object OnAddNewMpGroupClicked : SettingsUiAction
    data class OnAddNewMpGroup(val newMpGroup : MpGroup) : SettingsUiAction
    data class OnRemoveMpGroupClicked(val mpGroup :MpGroup) : SettingsUiAction
    data class OnRemoveMpGroup(val mpGroup :MpGroup) : SettingsUiAction
    data object OnEditMpGroupClicked : SettingsUiAction
    data class OnEditMpGroup(val mpGroup :MpGroup) : SettingsUiAction


}