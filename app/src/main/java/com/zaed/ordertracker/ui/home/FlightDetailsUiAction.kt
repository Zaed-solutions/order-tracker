package com.zaed.ordertracker.ui.home

import android.graphics.Color
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment

sealed interface FlightDetailsUiAction {
    data class AddShipment(
        val shipment: Shipment,
    ) : FlightDetailsUiAction

    data class UpdateShipment(
        val updatedShipment: Shipment,
    ) : FlightDetailsUiAction

    data class DeleteShipment(
        val shipmentId: String,
    ) : FlightDetailsUiAction

    data object NavigateBack : FlightDetailsUiAction

    data class AddNewMasterPackage(
        val masterPackage: MasterPackage,
    ) : FlightDetailsUiAction

    data class DeleteMasterPackage(
        val masterPackageId: String,
    ) : FlightDetailsUiAction

    data class EditMasterPackage(
        val masterPackage: MasterPackage,
    ) : FlightDetailsUiAction
}

