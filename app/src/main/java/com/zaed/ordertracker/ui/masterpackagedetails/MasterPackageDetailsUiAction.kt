package com.zaed.ordertracker.ui.masterpackagedetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment

sealed interface MasterPackageDetailsUiAction {
    data class OnAddNewShipment(val shipment: Shipment) : MasterPackageDetailsUiAction
    data class OnEditShipment(val shipment: Shipment) : MasterPackageDetailsUiAction
    data class OnEditMasterPackage(val masterPackage: MasterPackage) : MasterPackageDetailsUiAction
    data class OnDeleteShipment(val shipmentId: String) : MasterPackageDetailsUiAction
    data object OnBack : MasterPackageDetailsUiAction
}