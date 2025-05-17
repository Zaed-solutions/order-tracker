package com.zaed.ordertracker.domain.usecase.flight

import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.repository.FlightRepository

class CreateFlightUseCase(
    private val flightRepository: FlightRepository
) {
    suspend operator fun invoke(
        flight: Flight
    ) = flightRepository.addFlight(flight)
}