package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.FlightRemoteDataSource
import com.zaed.ordertracker.data.source.remote.ShipmentRemoteDataSource
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.repository.FlightRepository
import com.zaed.ordertracker.domain.utils.FlightHasUnprocessedShipmentsException

class FlightRepositoryImpl(
    private val flightRemoteDataSource: FlightRemoteDataSource,
    private val shipmentRemoteDataSource: ShipmentRemoteDataSource,
) : FlightRepository {
    override fun getAllFlights() = flightRemoteDataSource.getAllFlights()

    override suspend fun addFlight(flight: Flight) = flightRemoteDataSource.addFlight(flight)

    override suspend fun updateFlight(flight: Flight) = flightRemoteDataSource.updateFlight(flight)

    override suspend fun deleteFlight(id: String): Result<Unit> =
        shipmentRemoteDataSource.doesFlightHaveUnExportedShipments(id).map {
            if (it) {
                Result.failure(FlightHasUnprocessedShipmentsException())
            } else {
                flightRemoteDataSource.deleteFlight(id)
            }
        }

    override suspend fun getFlightById(id: String) = flightRemoteDataSource.getFlightById(id)
}
