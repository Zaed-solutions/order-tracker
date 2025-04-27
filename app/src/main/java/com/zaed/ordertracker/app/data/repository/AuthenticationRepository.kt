package com.zaed.ordertracker.app.data.repository

interface AuthenticationRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Result<Unit>
}
