package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.FirebaseCredential

interface FirebaseCredentialDataSource {
    suspend fun getFirebaseCredential(): Result<FirebaseCredential>
}