package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.FileExportRepository

class SaveExportFolderNameUseCase(
    private val fileExportRepository: FileExportRepository
) {
    suspend operator fun invoke(folderName: String)  = fileExportRepository.saveExportFolderName(folderName)
}
