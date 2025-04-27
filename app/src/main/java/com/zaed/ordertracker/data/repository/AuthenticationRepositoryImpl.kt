package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSource
import com.zaed.ordertracker.domain.repository.AuthenticationRepository

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
