package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.UserRemoteSource
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDataSource: UserRemoteSource
) : UserRepository {
    override suspend fun getUsers() = userDataSource.getUsers()

    override suspend fun saveUser(user: User) = userDataSource.saveUser(user)

    override suspend fun deleteUser(userId: String) = userDataSource.deleteUser(userId)
}