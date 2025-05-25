package com.zaed.ordertracker.ui.flights

import com.zaed.ordertracker.domain.model.Flight

sealed interface FlightsUiAction {
    data class AddFlight(
        val flight: Flight,
    ) : FlightsUiAction

    data class EditFlight(
        val updatedFlight: Flight,
    ) : FlightsUiAction

    data class DeleteFlight(
        val flightId: String,
    ) : FlightsUiAction

    data class NavigateToFlightDetails(
        val flightId: String,
    ) : FlightsUiAction
    data object NavigateToSettings: FlightsUiAction
    data object ResetError: FlightsUiAction
}
