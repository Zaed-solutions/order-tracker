package com.zaed.ordertracker.ui.flightdetails

import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.model.User

data class FlightDetailsUiState(
    val isLoading: Boolean = true,
    val currentUser: User = User(),
    val allShipments: List<Shipment> = emptyList(),
    val displayShipments: List<Shipment> = emptyList(),
    val flight: Flight = Flight(),
    val masterPackages: List<MasterPackage> = emptyList(),
    val groups: List<MpGroup> = emptyList(),
    val selectedGroupId: String? = null,
    val error: String? = null
)
