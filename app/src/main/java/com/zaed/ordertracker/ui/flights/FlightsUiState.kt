package com.zaed.ordertracker.ui.flights

import com.zaed.ordertracker.domain.model.Flight

data class FlightsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val allFlights: List<Flight> = emptyList(),
    val displayedFlights: List<Flight> = emptyList(),
    val searchQuery: String = "",
    val isLoggedOut: Boolean = false,
)
