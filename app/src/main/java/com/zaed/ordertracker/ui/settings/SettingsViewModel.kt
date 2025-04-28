package com.zaed.ordertracker.ui.settings

import androidx.lifecycle.ViewModel
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(

): ViewModel(){
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()



    fun handleAction(action: SettingsUiAction) {
        when(action){
            is SettingsUiAction.OnAddNewMpGroup -> TODO()
            SettingsUiAction.OnAddNewMpGroupClicked -> TODO()
            is SettingsUiAction.OnAddNewUser -> TODO()
            SettingsUiAction.OnAddNewUserClicked -> TODO()
            SettingsUiAction.OnDriveEmailClicked -> TODO()
            is SettingsUiAction.OnEditMpGroup -> TODO()
            SettingsUiAction.OnEditMpGroupClicked -> TODO()
            is SettingsUiAction.OnEditUser -> TODO()
            SettingsUiAction.OnEditUserClicked -> TODO()
            SettingsUiAction.OnExportFolderNameClicked -> TODO()
            is SettingsUiAction.OnExportFolderNameUpdated -> TODO()
            SettingsUiAction.OnFirebasePasswordClicked -> TODO()
            is SettingsUiAction.OnFirebasePasswordUpdated -> TODO()
            is SettingsUiAction.OnRemoveMpGroup -> TODO()
            is SettingsUiAction.OnRemoveMpGroupClicked -> TODO()
            is SettingsUiAction.OnRemoveUser -> TODO()
            is SettingsUiAction.OnRemoveUserClicked -> TODO()
            SettingsUiAction.OnBackClicked -> TODO()
        }
    }

}


