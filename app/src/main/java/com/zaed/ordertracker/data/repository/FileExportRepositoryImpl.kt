package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.DriveRemoteSource
import com.zaed.ordertracker.data.source.remote.ExportationFolderDataSource
import com.zaed.ordertracker.data.source.remote.GoogleAuth
import com.zaed.ordertracker.domain.repository.FileExportRepository
import kotlinx.coroutines.flow.first
import java.io.File

class FileExportRepositoryImpl(
    private val exportationFolderDataSource: ExportationFolderDataSource,
    private val driveRemoteSource: DriveRemoteSource,
    private val googleAuth: GoogleAuth,
) : FileExportRepository {
    override suspend fun getExportFolderName() = exportationFolderDataSource.getExportFolderName()

    override suspend fun saveExportFolderName(folderName: String) = exportationFolderDataSource.saveExportFolderName(folderName)

    override suspend fun uploadExcelToFolder(file: File): Result<String> {
        return try {
            val account =
                googleAuth
                    .getSignedInAccount()
                    .getOrElse { return Result.failure(Exception("User not signed in to Google")) }
            val folderName =
                getExportFolderName().takeIf {
                    it.isNotEmpty()
                } ?: return Result.failure(Exception("Export folder name not set"))
            driveRemoteSource
                .uploadExcelToFolder(account, file, folderName)
                .first()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
