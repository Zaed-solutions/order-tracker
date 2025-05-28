package com.zaed.ordertracker.ui.masterpkggroupdetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup

data class MasterPackageGroupDetailsUiState(
    val flightId: String = "",
    val group: MpGroup = MpGroup(),
    val allMasterPackages: List<MasterPackage> = emptyList(),
    val displayedMasterPackages: List<MasterPackage> = emptyList(),
    val searchQuery: String = ""
)