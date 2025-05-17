package com.zaed.ordertracker.domain.usecase.flight

import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.repository.FlightRepository

class UpdateFlightUseCase(
    private val flightRepository: FlightRepository,
) {
    suspend operator fun invoke(updatedFlight: Flight) = flightRepository.updateFlight(updatedFlight)
}
