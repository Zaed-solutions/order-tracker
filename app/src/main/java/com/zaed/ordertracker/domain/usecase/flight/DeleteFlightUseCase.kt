package com.zaed.ordertracker.domain.usecase.flight

import com.zaed.ordertracker.domain.repository.FlightRepository

class DeleteFlightUseCase(
    private val flightRepository: FlightRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> = flightRepository.deleteFlight(id)
}
