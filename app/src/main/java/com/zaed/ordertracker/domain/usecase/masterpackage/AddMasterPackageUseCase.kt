package com.zaed.ordertracker.domain.usecase.masterpackage

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.repository.MpGroupRepository

class AddMasterPackageUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(masterPackage: MasterPackage): Result<Unit> {
        return repository.addNewMasterPackage(masterPackage)
    }
}

