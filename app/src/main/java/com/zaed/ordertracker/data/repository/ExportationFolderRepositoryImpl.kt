package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.ExportationFolderDataSource
import com.zaed.ordertracker.domain.repository.ExportationFolderRepository

class ExportationFolderRepositoryImpl(
    private val exportationFolderDataSource: ExportationFolderDataSource
) : ExportationFolderRepository {
    override suspend fun getExportFolderName() = exportationFolderDataSource.getExportFolderName()

    override suspend fun saveExportFolderName(folderName: String) = exportationFolderDataSource.saveExportFolderName(folderName)
}