package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.ExportationFolderRepository

class SaveExportFolderNameUseCase(
    private val exportationFolderRepository: ExportationFolderRepository
) {
    suspend operator fun invoke(folderName: String)  = exportationFolderRepository.saveExportFolderName(folderName)
}
