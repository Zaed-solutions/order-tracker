package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.User

class AuthenticationRemoteSourceImpl : AuthenticationRemoteSource {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<User> {
        TODO("Not yet implemented")
    }
}
