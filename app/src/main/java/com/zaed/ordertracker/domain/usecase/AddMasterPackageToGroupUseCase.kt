package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.repository.MpGroupRepository

class AddMasterPackageToGroupUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(groupId: String, masterPackage: MasterPackage): Result<Unit> {
        return repository.addMasterPackageToGroup(groupId, masterPackage)
    }
}
