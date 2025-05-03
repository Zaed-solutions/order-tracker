package com.zaed.ordertracker.data.source.local

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    val currentUserId: Flow<String?>
    suspend fun saveCurrentUserId(userId: String): Result<Unit>
}