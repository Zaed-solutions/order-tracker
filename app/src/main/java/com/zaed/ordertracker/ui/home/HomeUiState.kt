package com.zaed.ordertracker.ui.home

import com.zaed.ordertracker.domain.model.Shipment

data class HomeUiState(
    val isLoading: Boolean = true,
    val shipments: List<Shipment> = emptyList(),
)
