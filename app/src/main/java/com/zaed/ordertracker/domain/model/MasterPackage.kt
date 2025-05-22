package com.zaed.ordertracker.domain.model

data class MasterPackage(
    val id: String="",
    val name: String="",
    val count: Int = 1,
    val flightId: String = "",
    val type: MasterPackageType = MasterPackageType.T,
    val weightKg: Double = 0.0,
    val isExported: Boolean = false,
    val shipments: List<Shipment> = emptyList()
){
    val isPnd: Boolean
        get() = weightKg == 0.0
}

enum class MasterPackageType {
    T,B
}