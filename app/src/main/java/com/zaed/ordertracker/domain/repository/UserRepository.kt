package com.zaed.ordertracker.domain.repository

import com.zaed.ordertracker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<Result<List<User>>>
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
}