package com.zaed.ordertracker.ui.home

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
}
