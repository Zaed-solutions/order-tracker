package com.zaed.ordertracker.ui.flightdetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.Shipment
import java.io.File

sealed interface FlightDetailsUiAction {
    data class AddShipment(
        val shipment: Shipment,
    ) : FlightDetailsUiAction

    data object ResetNeedToLogin : FlightDetailsUiAction

    data object NavigateToSettings : FlightDetailsUiAction

    data object ExportShipments : FlightDetailsUiAction

    data object ReExportAllShipments : FlightDetailsUiAction

    data class UpdateShipment(
        val updatedShipment: Shipment,
    ) : FlightDetailsUiAction

    data class DeleteShipment(
        val shipmentId: String,
    ) : FlightDetailsUiAction

    data object NavigateBack : FlightDetailsUiAction

    data class UploadExportedShipments(
        val excelSheet: File,
    ) : FlightDetailsUiAction

    data object ResetSnackbarMessage : FlightDetailsUiAction

    data class AddNewMasterPackage(
        val masterPackage: MasterPackage,
    ) : FlightDetailsUiAction

    data class DeleteMasterPackage(
        val masterPackageId: String,
    ) : FlightDetailsUiAction

    data class EditMasterPackage(
        val masterPackage: MasterPackage,
    ) : FlightDetailsUiAction

    data class OnMasterPackageClicked(
        val masterPackage: MasterPackage,
    ) : FlightDetailsUiAction

    data class OnMasterPackageGroupClicked(
        val group: MpGroup,
    ) : FlightDetailsUiAction
}
