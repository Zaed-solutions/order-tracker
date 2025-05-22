package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import kotlinx.coroutines.flow.Flow

class GetMasterPackagesByFlightIdUseCase(
    private val repository: MpGroupRepository
) {
     operator fun invoke(flightId: String): Flow<Result<List<MasterPackage>>> {
        return repository.getMasterPackagesByFlightId(flightId)
    }
}
