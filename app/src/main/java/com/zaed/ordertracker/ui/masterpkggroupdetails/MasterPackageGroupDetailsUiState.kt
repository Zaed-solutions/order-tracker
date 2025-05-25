package com.zaed.ordertracker.ui.masterpkggroupdetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup

data class MasterPackageGroupDetailsUiState(
    val flightId: String = "",
    val group: MpGroup = MpGroup(),
    val masterPackages: List<MasterPackage> = emptyList()
)