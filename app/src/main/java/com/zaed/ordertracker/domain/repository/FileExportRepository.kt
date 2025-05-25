package com.zaed.ordertracker.domain.repository

interface FileExportRepository {
    suspend fun getExportFolderName(): String

    suspend fun saveExportFolderName(folderName: String): Result<Unit>

    suspend fun uploadExcelToFolder(file: java.io.File): Result<String>
}
