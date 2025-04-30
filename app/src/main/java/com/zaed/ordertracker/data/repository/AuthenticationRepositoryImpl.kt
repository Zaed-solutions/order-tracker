package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSource
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteSource: AuthenticationRemoteSource,
) : AuthenticationRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<Unit> =
        remoteSource
            .login(username, password)
            .mapCatching { user ->
                storeLocalUser(user)
            }

    private fun storeLocalUser(user: User) {
        TODO("Store user data locally")
    }
}
