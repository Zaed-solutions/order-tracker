package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.local.LocalStorage
import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSource
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.firstOrNull

class AuthenticationRepositoryImpl(
    private val remoteSource: AuthenticationRemoteSource,
    private val localStorage: LocalStorage,
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

    override suspend fun getCurrentUser(): Result<User> =
        remoteSource.getUserById(
            localStorage.currentUserId.firstOrNull()
                ?: throw IllegalStateException("No user logged in"),
        )

    private suspend fun storeLocalUser(user: User) {
        localStorage.saveCurrentUserId(user.id)
    }
}
