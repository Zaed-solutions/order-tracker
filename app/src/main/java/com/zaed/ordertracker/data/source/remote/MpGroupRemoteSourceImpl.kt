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
            mpGroupsCollection.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    crashlytics.recordException(exception)
                    return@addSnapshotListener
                }
                val mpGroups = snapshot?.documents?.mapNotNull { it.toObject(MpGroup::class.java) }
                trySend(Result.success(mpGroups ?: emptyList()))
            }
        } catch (e: Exception) {
            crashlytics.recordException(e)
            trySend(Result.failure(e))
        }
        awaitClose {  }
    }

    override suspend fun saveMpGroup(mpGroup: MpGroup): Result<String> {
        return if (mpGroup.id.isEmpty()) {
            addMpGroup(mpGroup)
        } else {
            updateMpGroup(mpGroup)
        }
    }
    private suspend fun addMpGroup(mpGroup: MpGroup): Result<String> {
        try {
            val docRef = mpGroupsCollection.document()
            docRef.set(mpGroup.copy(id = docRef.id)).await()
            return Result.success(docRef.id)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }
    private suspend fun updateMpGroup(mpGroup: MpGroup): Result<String> {
        try {
            mpGroupsCollection.document(mpGroup.id).set(mpGroup).await()
            return Result.success(mpGroup.id)
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
        try {
            val document = mpGroupsCollection.document(groupId).get().await()
            val mpGroup = document.toObject(MpGroup::class.java) ?: return Result.failure(Exception("MpGroup not found"))
            return Result.success(mpGroup)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun addMasterPackageToGroup(
        groupId: String,
        masterPackageDto: MasterPackage
    ): Result<Unit> {
        try {
            val groupDoc = mpGroupsCollection.document(groupId).get().await()
            val group = groupDoc.toObject(MpGroup::class.java) ?: return Result.failure(Exception("MpGroup not found"))

            // Add the master package to the group's master packages list
            val updatedMasterPackages = group.masterPackages.toMutableList()
            updatedMasterPackages.add(masterPackageDto)

            // Update the group with the new master packages list
            val updatedGroup = group.copy(masterPackages = updatedMasterPackages)
            mpGroupsCollection.document(groupId).set(updatedGroup).await()

            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun updateMasterPackage(masterPackageDto: MasterPackage): Result<Unit> {
        try {
            // First, find all groups that contain this master package
            val querySnapshot = mpGroupsCollection.get().await()
            val groups = querySnapshot.documents.mapNotNull { it.toObject(MpGroup::class.java) }

            // For each group that contains the master package, update it
            for (group in groups) {
                val masterPackageIndex = group.masterPackages.indexOfFirst { it.id == masterPackageDto.id }
                if (masterPackageIndex != -1) {
                    // Create a new list with the updated master package
                    val updatedMasterPackages = group.masterPackages.toMutableList()
                    updatedMasterPackages[masterPackageIndex] = masterPackageDto

                    // Update the group with the new master packages list
                    val updatedGroup = group.copy(masterPackages = updatedMasterPackages)
                    mpGroupsCollection.document(group.id).set(updatedGroup).await()
                }
            }

            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun updateMpGroupBackgroundColor(
        groupId: String,
        color: String
    ): Result<Unit> {
        try {
            // Get the current group
            val groupDoc = mpGroupsCollection.document(groupId).get().await()
            val group = groupDoc.toObject(MpGroup::class.java) ?: return Result.failure(Exception("MpGroup not found"))

            // Update the group with the new color
            // Note: The color parameter is a String, but MpGroup.color is an Int
            // We need to parse the color string to an integer
            val colorInt = try {
                color.toInt()
            } catch (e: NumberFormatException) {
                return Result.failure(Exception("Invalid color format"))
            }

            val updatedGroup = group.copy(color = colorInt)
            mpGroupsCollection.document(groupId).set(updatedGroup).await()

            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun exportMasterPackages(
        groupId: String,
        masterPackageIds: List<String>
    ): Result<Unit> {
        try {
            // Get the current group
            val groupDoc = mpGroupsCollection.document(groupId).get().await()
            val group = groupDoc.toObject(MpGroup::class.java) ?: return Result.failure(Exception("MpGroup not found"))

            // Mark the group as exported
            val updatedGroup = group.copy(isExported = true)
            mpGroupsCollection.document(groupId).set(updatedGroup).await()

            // In a real implementation, you might want to do something with the masterPackageIds
            // like mark them as exported in a separate collection or perform some export operation

            return Result.success(Unit)
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }
}
