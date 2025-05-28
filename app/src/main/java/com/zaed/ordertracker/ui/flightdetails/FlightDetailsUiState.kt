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
    val shipmentSearchQuery: String = "",
    val flight: Flight = Flight(),
    val allMasterPackages: List<MasterPackage> = emptyList(),
    val displayedMasterPackages: List<MasterPackage> = emptyList(),
    val allGroups: List<MpGroup> = emptyList(),
    val displayedGroups: List<MpGroup> = emptyList(),
    val masterPackageSearchQuery: String = "",
    val selectedGroupId: String? = null,
    val snackbarMessage: String? = null,
    val needToLogin: Boolean = false,
)
