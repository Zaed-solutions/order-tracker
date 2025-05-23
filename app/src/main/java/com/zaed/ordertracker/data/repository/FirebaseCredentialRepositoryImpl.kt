package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.FirebaseCredentialDataSource
import com.zaed.ordertracker.domain.repository.FirebaseCredentialRepository

class FirebaseCredentialRepositoryImpl(
    private val firebaseCredentialDataSource: FirebaseCredentialDataSource
) : FirebaseCredentialRepository {
    override suspend fun getFirebaseCredential() = firebaseCredentialDataSource.getFirebaseCredential()
}


