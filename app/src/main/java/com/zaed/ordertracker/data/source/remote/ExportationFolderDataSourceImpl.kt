package com.zaed.ordertracker.data.source.remote

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExportationFolderDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
) : ExportationFolderDataSource {
    private val exportationFolderCollection = firestore.collection("exportationFolder")
    override suspend fun getExportFolderName(): String {
        val result = exportationFolderCollection.get().await()
        val folderName = result.documents.firstOrNull()?.getString("folderName")
        return folderName ?: ""
    }

    override suspend fun saveExportFolderName(folderName: String): Result<Unit> {
        exportationFolderCollection.document("f6hbvRLg09eY17YweHNn").set(mapOf("folderName" to folderName)).await()
        return Result.success(Unit)
    }
}