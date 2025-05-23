package com.zaed.ordertracker.data.source.remote.model.mapper

import com.zaed.ordertracker.data.source.remote.model.ShipmentDto
import com.zaed.ordertracker.domain.model.Shipment
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Shipment.toShipmentDto() =
    ShipmentDto(
        id = id,
        shipmentNumber = shipmentNumber,
        quantity = quantity,
        weight = weight,
        masterPackageId = masterPackageId,
        addedAtEpochSeconds = addedAt.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
        type = type.name,
        addedById = addedById,
        exported = exported,
        flightId = flightId,
    )

fun ShipmentDto.toShipment() =
    Shipment(
        id = id,
        shipmentNumber = shipmentNumber,
        masterPackageId = masterPackageId,
        quantity = quantity,
        weight = weight,
        addedAt =
            Instant
                .fromEpochSeconds(addedAtEpochSeconds)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        type = Shipment.Type.valueOf(type),
        addedById = addedById,
        exported = exported,
        flightId = flightId,
    )
