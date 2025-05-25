package com.zaed.ordertracker.domain.usecase.masterpackage

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMasterPackageWithShipmentsUseCase(
    private val mpGroupRepository: MpGroupRepository,
    private val shipmentRepository: ShipmentRepository
) {
    operator fun invoke(masterPackageId: String): Flow<Result<MasterPackage>> = flow {
        try {
            val masterPackage = mpGroupRepository.getMasterPackageById(masterPackageId).getOrThrow()
            shipmentRepository.getShipmentsByMasterPackageId(masterPackageId).collect { result ->
                result.fold(
                    onSuccess = { shipments ->
                        emit(Result.success(masterPackage.copy(shipments = shipments)))
                    },
                    onFailure = {
                        emit(Result.failure(it))
                    }
                )
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
