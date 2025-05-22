package com.zaed.ordertracker.ui.masterpackagedetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment

data class MasterPackageDetailsUiState(
    val masterPackage: MasterPackage = MasterPackage(),
    val shipments : List<Shipment> = emptyList(),
)