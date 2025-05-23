package com.zaed.ordertracker.domain.repository

interface ExportationFolderRepository {
    suspend fun getExportFolderName(): String
    suspend fun saveExportFolderName(folderName: String): Result<Unit>
}