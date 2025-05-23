package com.zaed.ordertracker.domain.repository

import com.zaed.ordertracker.domain.model.FirebaseCredential
import com.zaed.ordertracker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseCredentialRepository {
    suspend fun getFirebaseCredential(): Result<FirebaseCredential>
}