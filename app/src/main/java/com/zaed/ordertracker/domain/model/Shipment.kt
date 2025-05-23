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
) {
    enum class Type {
        T,
        B,
    }
}