package com.zaed.ordertracker.domain.model

data class MasterPackage(
    val id: String,
    val name: String,
    val count: Int = 1,
    val type: String = "T",
    val weightKg: Double = 0.0,
    val isPnd: Boolean = false,
    val isExported: Boolean = false,
    val shipments: List<Shipment> = emptyList()
)