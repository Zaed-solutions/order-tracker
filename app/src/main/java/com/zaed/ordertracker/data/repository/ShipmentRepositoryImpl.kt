package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.ShipmentRemoteDataSource
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import kotlinx.coroutines.flow.Flow

class ShipmentRepositoryImpl(
    private val shipmentRemoteSource: ShipmentRemoteDataSource,
) : ShipmentRepository {
    override fun getFlightShipments(flightId: String): Flow<Result<List<Shipment>>> = shipmentRemoteSource.getFlightShipments(flightId)

    override suspend fun createShipment(shipment: Shipment): Result<Unit> = shipmentRemoteSource.createShipment(shipment)

    override suspend fun updateShipment(updatedShipment: Shipment): Result<Unit> = shipmentRemoteSource.updateShipment(updatedShipment)

    override suspend fun deleteShipment(shipmentId: String): Result<Unit> = shipmentRemoteSource.deleteShipment(shipmentId)

    override suspend fun getShipmentsByMasterPackageId(masterPackageId: String): Flow<Result<List<Shipment>>> =
        shipmentRemoteSource.getShipmentsByMasterPackageId(masterPackageId)

    override suspend fun updateFlightShipmentsExportedStatus(flightId: String): Result<Unit> =
        shipmentRemoteSource.updateFlightShipmentsExportedStatus(flightId)
}
