package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.ExportationFolderRepository

class GetExportFolderNameUseCase(
    private val exportationFolderRepository: ExportationFolderRepository
) {
    suspend operator fun invoke() = exportationFolderRepository.getExportFolderName()
}
