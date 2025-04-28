package com.zaed.ordertracker.ui.settings

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.User

data class SettingsUiState(
    val driveEmail : String = "",
    val exportFolderName : String = "",
    val firebaseEmail : String = "",
    val firebasePassword : String = "",
    val users :List<User> = emptyList(),
    val mpGroups :List<MpGroup> = emptyList()
)