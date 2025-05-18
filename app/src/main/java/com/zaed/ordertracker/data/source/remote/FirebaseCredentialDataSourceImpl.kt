package com.zaed.ordertracker.data.source.remote

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.domain.model.FirebaseCredential
import kotlinx.coroutines.tasks.await

class FirebaseCredentialDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
) : FirebaseCredentialDataSource {
    private val credentialsCollection = firestore.collection("credentials")
    override suspend fun getFirebaseCredential(): Result<FirebaseCredential> {
        val result = credentialsCollection.document("dtMWO59Z5F1xuuA0jnj6").get().await()
        val credential = result.toObject(FirebaseCredential::class.java)
        return if (credential != null) {
            Result.success(credential)
        } else {
            Result.failure(Exception("No credentials found"))
        }
    }
}