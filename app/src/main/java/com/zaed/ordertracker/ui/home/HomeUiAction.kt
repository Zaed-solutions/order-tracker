package com.zaed.ordertracker.ui.home

import android.graphics.Color
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment

sealed interface HomeUiAction {
    data class AddShipment(
        val shipment: Shipment,
    ) : HomeUiAction

    data class UpdateShipment(
        val updatedShipment: Shipment,
    ) : HomeUiAction

    data class DeleteShipment(
        val shipmentId: String,
    ) : HomeUiAction

    data object LoadGroups : HomeUiAction
    data class SelectGroup(val groupId: String) : HomeUiAction
    data class AddMasterPackage(
        val groupId: String,
        val name: String,
        val count: Int = 1,
        val type: String = "T"
    ) : HomeUiAction
    data class UpdateMasterPackage(
        val masterPackage: MasterPackage
    ) : HomeUiAction
    data class UpdateGroupBackgroundColor(
        val groupId: String,
        val color: Int
    ) : HomeUiAction
    data class ExportMasterPackages(
        val groupId: String,
        val masterPackageIds: List<String>
    ) : HomeUiAction
}

