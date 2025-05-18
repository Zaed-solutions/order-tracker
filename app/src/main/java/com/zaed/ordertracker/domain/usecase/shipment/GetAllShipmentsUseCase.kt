package com.zaed.ordertracker.domain.usecase.shipment

import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import kotlinx.coroutines.flow.Flow

class GetAllShipmentsUseCase(
    private val shipmentRepository: ShipmentRepository,
) {
    operator fun invoke(): Flow<Result<List<Shipment>>> = shipmentRepository.getAllShipments()
}
