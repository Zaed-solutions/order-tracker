package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.Flight
import kotlinx.coroutines.flow.Flow

interface FlightRemoteDataSource {
    fun getAllFlights(): Flow<Result<List<Flight>>>

    suspend fun addFlight(flight: Flight): Result<Unit>

    suspend fun updateFlight(flight: Flight): Result<Unit>

    suspend fun deleteFlight(id: String): Result<Unit>

    suspend fun getFlightById(id: String): Result<Flight>
}