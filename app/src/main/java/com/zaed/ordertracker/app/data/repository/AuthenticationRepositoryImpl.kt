package com.zaed.ordertracker.app.data.repository

import com.zaed.ordertracker.app.data.source.remote.AuthenticationRemoteSource

class AuthenticationRepositoryImpl(
    private val remoteSource: AuthenticationRemoteSource
) : AuthenticationRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}
