package com.zaed.ordertracker.ui.home

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
}
