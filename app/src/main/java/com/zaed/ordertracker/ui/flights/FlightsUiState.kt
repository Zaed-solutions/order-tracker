package com.zaed.ordertracker.ui.flights

import com.zaed.ordertracker.domain.model.Flight

data class FlightsUiState(
    val isLoading: Boolean = true,
    val flights: List<Flight> = emptyList()
)
