package com.zaed.ordertracker.domain.usecase.flight

import com.zaed.ordertracker.domain.repository.FlightRepository

class GetAllFlightsUseCase(
    private val flightRepository: FlightRepository
) {
    operator fun invoke() = flightRepository.getAllFlights()
}