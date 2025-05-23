package com.zaed.ordertracker.domain.usecase.shipment

import com.zaed.ordertracker.domain.repository.ShipmentRepository

class DeleteShipmentUseCase(
    private val repo: ShipmentRepository,
) {
    suspend operator fun invoke(shipmentId: String): Result<Unit> = repo.deleteShipment(shipmentId)
}
