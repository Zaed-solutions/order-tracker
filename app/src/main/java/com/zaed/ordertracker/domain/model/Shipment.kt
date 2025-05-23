package com.zaed.ordertracker.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Shipment(
    val id: String = "",
    val masterPackageId: String = "",
    val shipmentNumber: String = "",
    val quantity: Int = 1,
    val weight: Double = 0.0,
    val addedAt: LocalDateTime =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault()),
    val type: Type = Type.T,
    val addedById: String = "",
    val exported: Boolean = false,
    val flightId: String = "",
    val masterPackageWeight: Double = 0.0,
    val masterPackageName: String = "",
    val userName: String = "",
    val userId: String = "",
) {
    enum class Type {
        T,
        B,
    }
}