package com.zaed.ordertracker.data.source.remote

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import java.util.Collections

class DriveRemoteSourceImpl(
    private val context: Context,
) : DriveRemoteSource {
    private fun createDriveService(account: GoogleSignInAccount): Drive {
        try {
            val credential =
                GoogleAccountCredential.usingOAuth2(
                    context,
                    Collections.singleton(DriveScopes.DRIVE_FILE),
                )
            credential.selectedAccount = account.account
            return Drive
                .Builder(
                    com.google.api.client.http.javanet
                        .NetHttpTransport(),
                    com.google.api.client.json.gson
                        .GsonFactory(),
                    credential,
                ).setApplicationName("Barcode Scanner") // Replace with your app's name
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("UPLOAD_ERROR", "Failed to create Drive service: ${e.message}", e)
            throw e
        }
    }

    override fun uploadExcelToFolder(
        account: GoogleSignInAccount,
        file: File,
        folderName: String,
    ): Flow<Result<String>> =
        callbackFlow {
            try {
                val driveService = createDriveService(account)

                // Find or create the folder
                val folderId = findOrCreateFolder(account, folderName)
                if (folderId.isEmpty()) {
                    trySend(Result.failure(Exception("Failed to create or find folder: $folderName")))
                }

                // Create file metadata
                val fileMetadata =
                    com.google.api.services.drive.model.File().apply {
                        name = file.name
                        parents = Collections.singletonList(folderId)
                    }

                // Create file content (Excel MIME type)
                val mediaContent =
                    FileContent(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        file,
                    )

                // Upload the file
                val uploadedFile =
                    driveService
                        .files()
                        .create(fileMetadata, mediaContent)
                        .setFields("id, name")
                        .execute()

                Log.d(
                    "UPLOAD_SUCCESS",
                    "Excel file uploaded - ID: ${uploadedFile.id}, Name: ${uploadedFile.name}",
                )
                trySend(Result.success(uploadedFile.id))
            } catch (e: Exception) {
                Log.e("UPLOAD_ERROR", "Failed to upload Excel file: ${e.message}", e)
                trySend(Result.failure(e))
            }
            awaitClose { }
        }

    private fun findOrCreateFolder(
        account: GoogleSignInAccount,
        folderName: String,
    ): String {
        try {
            val driveService = createDriveService(account)

            // First, check if folder exists
            val query = "name = '$folderName' and mimeType = 'application/vnd.google-apps.folder'"
            val existingFolders =
                driveService
                    .files()
                    .list()
                    .setQ(query)
                    .setFields("files(id, name)")
                    .execute()
                    .files

            if (existingFolders.isNotEmpty()) {
                Log.d("FOLDER_FOUND", "Folder already exists: ${existingFolders.first().id}")
                return existingFolders.first().id
            }

            // Create folder if it doesn't exist
            val folderMetadata =
                com.google.api.services.drive.model.File().apply {
                    name = folderName
                    mimeType = "application/vnd.google-apps.folder"
                }

            val folder =
                driveService
                    .files()
                    .create(folderMetadata)
                    .setFields("id, name")
                    .execute()

            Log.d("FOLDER_CREATED", "New folder created - ID: ${folder.id}, Name: ${folder.name}")
            return folder.id
        } catch (e: Exception) {
            Log.e("FOLDER_ERROR", "Failed to find or create folder: ${e.message}", e)
            return ""
        }
    }
}
