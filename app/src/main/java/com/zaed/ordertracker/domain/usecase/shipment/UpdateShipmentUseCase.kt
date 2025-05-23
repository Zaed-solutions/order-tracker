package com.zaed.ordertracker.domain.usecase.shipment

import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.repository.ShipmentRepository

class UpdateShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) {
    suspend operator fun invoke(updatedShipment: Shipment): Result<Unit> = shipmentRepository.updateShipment(updatedShipment)
}
