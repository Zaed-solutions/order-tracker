package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.MpGroupRepository

class ExportMasterPackagesUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(groupId: String, masterPackageIds: List<String>): Result<Unit> {
        return repository.exportMasterPackages(groupId, masterPackageIds)
    }
}