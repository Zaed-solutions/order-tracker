package com.zaed.ordertracker.ui.home

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.Shipment

data class FlightDetailsUiState(
    val isLoading: Boolean = true,
    val shipments: List<Shipment> = emptyList(),
    val flightId: String = "",
    val masterPackages: List<MasterPackage> = emptyList(),
    val groups: List<MpGroup> = emptyList(),
    val selectedGroupId: String? = null,
    val error: String? = null
)
