package com.zaed.ordertracker.data.source.remote

interface ExportationFolderDataSource {
    suspend fun getExportFolderName(): String
    suspend fun saveExportFolderName(folderName: String): Result<Unit>
}
