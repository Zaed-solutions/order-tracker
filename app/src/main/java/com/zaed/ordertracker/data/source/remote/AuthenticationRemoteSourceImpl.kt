package com.zaed.ordertracker.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.utils.UserNotFoundException
import kotlinx.coroutines.tasks.await

class AuthenticationRemoteSourceImpl(
    firestore: FirebaseFirestore,
) : AuthenticationRemoteSource {
    private val usersCollection = firestore.collection("users")

    override suspend fun login(
        username: String,
        password: String,
    ): Result<User> =
        try {
            val querySnapshot =
                usersCollection
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get()
                    .await()
            querySnapshot
                .documents
                .firstOrNull()
                ?.toObject(User::class.java)
                .takeIf {
                    it != null
                }?.let { user ->
                    Result.success(user)
                } ?: Result.failure(UserNotFoundException())
        } catch (e: Exception) {
            Result.failure(e)
        }
}
