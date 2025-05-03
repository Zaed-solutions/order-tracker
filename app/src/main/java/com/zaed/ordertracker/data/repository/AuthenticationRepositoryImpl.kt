package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.local.LocalStorage
import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSource
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteSource: AuthenticationRemoteSource,
    private val localStorage: LocalStorage
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

    private suspend fun storeLocalUser(user: User) {
        localStorage.saveCurrentUserId(user.id)
    }
}
