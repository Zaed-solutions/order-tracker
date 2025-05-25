package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.FileExportRepository

class GetExportFolderNameUseCase(
    private val fileExportRepository: FileExportRepository
) {
    suspend operator fun invoke() = fileExportRepository.getExportFolderName()
}
