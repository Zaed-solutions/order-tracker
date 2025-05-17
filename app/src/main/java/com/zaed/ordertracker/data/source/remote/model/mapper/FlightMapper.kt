package com.zaed.ordertracker.data.source.remote.model.mapper

import com.zaed.ordertracker.data.source.remote.model.FlightDto
import com.zaed.ordertracker.domain.model.Flight
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun FlightDto.toFlight() =
    Flight(
        id = id,
        name = name,
        date =
            Instant
                .fromEpochSeconds(dateEpochSeconds)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
    )

fun Flight.toFlightDto() =
    FlightDto(
        id = id,
        name = name,
        dateEpochSeconds = date.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
    )
