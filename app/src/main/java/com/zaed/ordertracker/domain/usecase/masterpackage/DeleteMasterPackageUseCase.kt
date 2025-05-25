package com.zaed.ordertracker.domain.usecase.masterpackage

import com.zaed.ordertracker.domain.repository.MpGroupRepository

class DeleteMasterPackageUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(masterPackageId: String): Result<Unit> {
        return repository.deleteMasterPackage(masterPackageId)
    }
}