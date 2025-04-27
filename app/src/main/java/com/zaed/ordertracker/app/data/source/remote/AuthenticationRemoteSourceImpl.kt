package com.zaed.ordertracker.app.data.source.remote

import com.zaed.ordertracker.app.data.model.User

class AuthenticationRemoteSourceImpl : AuthenticationRemoteSource {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<User> {
        TODO("Not yet implemented")
    }
}
