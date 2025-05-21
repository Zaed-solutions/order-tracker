package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.Shipment
import kotlinx.coroutines.flow.Flow

interface ShipmentRemoteDataSource {
    fun getFlightShipments(flightId: String): Flow<Result<List<Shipment>>>

    suspend fun createShipment(shipment: Shipment): Result<Unit>

    suspend fun updateShipment(updatedShipment: Shipment): Result<Unit>

    suspend fun deleteShipment(shipmentId: String): Result<Unit>
}