package com.zaed.ordertracker.data.source.remote.model

data class ShipmentDto(
    val id: String = "",
    val masterPackageId: String = "",
    val shipmentNumber: String = "",
    val quantity: Int = 1,
    val weight: Double = 0.0,
    val addedAtEpochSeconds: Long = 0L,
    val type: String = "T",
    val addedById: String = "",
    val addedByName: String = "",
    val exported: Boolean = false,
    val flightId: String = "",
    val masterPackageWeight: Double = 0.0,
    val masterPackageName: String = "",
)
