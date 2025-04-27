package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.User

interface AuthenticationRemoteSource {
    suspend fun login(
        username: String,
        password: String,
    ): Result<User>
}
