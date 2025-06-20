package com.zaed.ordertracker.data.source.remote

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRemoteSourceImpl(
    private val firestore: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics,
) : UserRemoteSource {
    private val usersCollection = firestore.collection("users")

    override suspend fun getUsers(): Flow<Result<List<User>>> =
        callbackFlow {
            try {
                usersCollection.addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        crashlytics.recordException(exception)
                        return@addSnapshotListener
                    }
                    val users = snapshot?.documents?.mapNotNull { it.toObject(User::class.java) }
                    trySend(Result.success(users ?: emptyList()))
                }
            } catch (e: Exception) {
                crashlytics.recordException(e)
                trySend(Result.failure(e))
            }
            awaitClose { }
        }

    override suspend fun saveUser(user: User): Result<Unit> =
        if (user.id.isEmpty()) {
            addUser(user)
        } else {
            updateUser(user)
        }

    private suspend fun addUser(user: User): Result<Unit> {
        try {
            val docRef = usersCollection.document()
            docRef.set(user.copy(id = docRef.id)).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    private suspend fun updateUser(user: User): Result<Unit> {
        try {
            usersCollection.document(user.id).set(user).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        try {
            usersCollection.document(userId).delete().await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }
}
