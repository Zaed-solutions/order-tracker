package com.zaed.ordertracker.ui.settings

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User


data class SettingsUiState(
    val currentAccount: GoogleSignInAccount? = null,
    val exportFolderName: String = "",
    val firebaseEmail: String = "",
    val firebasePassword: String = "",
    val users: List<User> = emptyList(),
    val mpGroups: List<MpGroup> = emptyList(),

    // Editing states
    val isEditingDriveEmail: Boolean = false,
    val isEditingExportFolder: Boolean = false,
    val isEditingFirebasePassword: Boolean = false,

    // Selected items for editing
    val selectedUser: User? = null,
    val selectedMpGroup: MpGroup? = null,

    // Dialog states
    val isShowingAddUserDialog: Boolean = false,
    val isShowingEditUserDialog: Boolean = false,
    val isShowingDeleteUserDialog: Boolean = false,
    val isShowingAddMpGroupDialog: Boolean = false,
    val isShowingEditMpGroupDialog: Boolean = false,
    val isShowingDeleteMpGroupDialog: Boolean = false,

    // Temporary values for dialogs
    val tempUsername: String = "",
    val tempPassword: String = "",
    val tempMpGroupName: String = "",
    val tempMpGroupColor: Int = 0xFF000000.toInt()
)