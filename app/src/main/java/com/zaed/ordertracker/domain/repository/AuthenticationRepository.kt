package com.zaed.ordertracker.domain.repository

import com.zaed.ordertracker.domain.model.User

interface AuthenticationRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Result<Unit>

    suspend fun getCurrentUser(): Result<User>
    suspend fun logout(): Result<Unit>
}
