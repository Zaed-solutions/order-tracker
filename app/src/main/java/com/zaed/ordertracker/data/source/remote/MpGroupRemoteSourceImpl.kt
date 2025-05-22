package com.zaed.ordertracker.data.source.remote

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MpGroupRemoteSourceImpl(
    private val firestore: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
) : MpGroupRemoteSource {
    private val mpGroupsCollection = firestore.collection("mpGroups")
    override suspend fun getMpGroups(): Flow<Result<List<MpGroup>>> = callbackFlow {
        try {
            val listener = mpGroupsCollection.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    crashlytics.recordException(exception)
                    return@addSnapshotListener
                }
                val mpGroups = snapshot?.documents?.mapNotNull { it.toObject(MpGroup::class.java) }
                trySend(Result.success(mpGroups ?: emptyList()))
            }
            awaitClose { listener.remove() }
        } catch (e: Exception) {
            crashlytics.recordException(e)
            trySend(Result.failure(e))
        }
    }

    override suspend fun saveMpGroup(mpGroup: MpGroup): Result<Unit> {
        return if (mpGroup.id.isEmpty()) {
            addMpGroup(mpGroup)
        } else {
            updateMpGroup(mpGroup)
        }
    }
    private suspend fun addMpGroup(mpGroup: MpGroup): Result<Unit> {
        try {
            val docRef = mpGroupsCollection.document()
            docRef.set(mpGroup.copy(id = docRef.id)).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }
    private suspend fun updateMpGroup(mpGroup: MpGroup): Result<Unit> {
        try {
            mpGroupsCollection.document(mpGroup.id).set(mpGroup).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun deleteMpGroup(mpGroupId: String): Result<Unit> {
        try {
            mpGroupsCollection.document(mpGroupId).delete().await()
            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun getMpGroupById(groupId: String): Result<MpGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun addMasterPackageToGroup(
        groupId: String,
        masterPackageDto: MasterPackage
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMasterPackage(masterPackageDto: MasterPackage): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMpGroupBackgroundColor(
        groupId: String,
        color: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun exportMasterPackages(
        groupId: String,
        masterPackageIds: List<String>
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}