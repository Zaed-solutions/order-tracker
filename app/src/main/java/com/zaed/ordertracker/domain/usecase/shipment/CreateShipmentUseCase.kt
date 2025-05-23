package com.zaed.ordertracker.domain.usecase.shipment

import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.repository.ShipmentRepository

class CreateShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) {
    suspend operator fun invoke(shipment: Shipment): Result<Unit> = shipmentRepository.createShipment(shipment)
}
