package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.repository.MpGroupRepository

class EditMasterPackageUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(masterPackage: MasterPackage): Result<Unit> {
        return repository.editMasterPackage(masterPackage)
    }
}