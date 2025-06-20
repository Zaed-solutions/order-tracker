package com.zaed.ordertracker.ui.flights

import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.model.User

data class FlightsUiState(
    val isLoading: Boolean = true,
    val currentUser: User = User(),
    val error: String? = null,
    val allFlights: List<Flight> = emptyList(),
    val displayedFlights: List<Flight> = emptyList(),
    val searchQuery: String = "",
    val isLoggedOut: Boolean = false,
)
