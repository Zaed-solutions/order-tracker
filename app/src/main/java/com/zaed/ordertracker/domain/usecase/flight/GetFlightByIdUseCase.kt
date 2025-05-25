package com.zaed.ordertracker.domain.usecase.flight

import com.zaed.ordertracker.domain.repository.FlightRepository

class GetFlightByIdUseCase(
    private val flightRepository: FlightRepository,
) {
    suspend operator fun invoke(id: String) = flightRepository.getFlightById(id)
}
