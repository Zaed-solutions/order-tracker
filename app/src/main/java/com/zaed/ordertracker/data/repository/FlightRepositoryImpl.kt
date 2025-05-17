package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.FlightRemoteDataSource
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.repository.FlightRepository

class FlightRepositoryImpl(
    private val flightRemoteDataSource: FlightRemoteDataSource,
) : FlightRepository {
    override fun getAllFlights() = flightRemoteDataSource.getAllFlights()

    override suspend fun addFlight(flight: Flight) = flightRemoteDataSource.addFlight(flight)

    override suspend fun updateFlight(flight: Flight) = flightRemoteDataSource.updateFlight(flight)

    override suspend fun deleteFlight(id: String) = flightRemoteDataSource.deleteFlight(id)
}
