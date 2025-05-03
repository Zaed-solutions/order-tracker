package com.zaed.ordertracker.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "local_settings")

class LocalStorageImpl(
    private val context: Context,
) : LocalStorage {
    companion object {
        private val CURRENT_USER_ID_KEY = stringPreferencesKey("currentUserId")
    }

    override suspend fun saveCurrentUserId(userId: String): Result<Unit> =
        try {
            context.dataStore.edit { preferences ->
                preferences[CURRENT_USER_ID_KEY] = userId
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override val currentUserId: Flow<String?> =
        context.dataStore.data
            .map { preferences ->
                preferences[CURRENT_USER_ID_KEY]
            }
}
