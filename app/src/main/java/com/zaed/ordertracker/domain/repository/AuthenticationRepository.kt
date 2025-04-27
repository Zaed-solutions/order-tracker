package com.zaed.ordertracker.domain.repository

interface AuthenticationRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Result<Unit>
}
