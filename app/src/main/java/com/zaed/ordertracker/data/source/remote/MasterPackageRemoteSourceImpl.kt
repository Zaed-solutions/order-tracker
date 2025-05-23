package com.zaed.ordertracker.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.zaed.ordertracker.domain.model.MasterPackage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MasterPackageRemoteSourceImpl(
    private val firestore: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
) : MasterPackageRemoteSource {
    private val masterPackagesCollection = firestore.collection("masterPackages")
    override suspend fun addNewMasterPackage(masterPackage: MasterPackage): Result<Unit> {
        try {
            val document = masterPackagesCollection.document()
            document.set(masterPackage.copy(id = document.id)).await()
            return Result.success(Unit)
        }catch (e: Exception){
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override fun getMasterPackagesByFlightId(flightId: String): Flow<Result<List<MasterPackage>>> = callbackFlow{
            val filter = Filter.equalTo(MasterPackage::flightId.name, flightId)
            masterPackagesCollection.where(filter).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    crashlytics.recordException(error)
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                } else {
                    Log.d("MasterPackageRemote", "getMasterPackagesByFlightId: snapshot: ${snapshot?.documents?.size}")
                    val masterPackages = snapshot?.documents?.mapNotNull {
                        it.toObject(MasterPackage::class.java)
                    } ?: emptyList()
                    trySend(Result.success(masterPackages))
                    return@addSnapshotListener
                }
            }
        awaitClose{}
    }
    override fun getMasterPackagesByGroupId(groupId: String): Flow<Result<List<MasterPackage>>> = callbackFlow{
        val filter = Filter.equalTo(MasterPackage::groupId.name, groupId)
        masterPackagesCollection.where(filter).addSnapshotListener { snapshot, error ->
            if (error != null) {
                crashlytics.recordException(error)
                trySend(Result.failure(error))
                return@addSnapshotListener
            } else {
                Log.d(MasterPackageRemoteSourceImpl::class.simpleName, "${MasterPackageRemoteSourceImpl::getMasterPackagesByGroupId.name}: snapshot: ${snapshot?.documents?.size}")
                val masterPackages = snapshot?.documents?.mapNotNull {
                    it.toObject(MasterPackage::class.java)
                } ?: emptyList()
                trySend(Result.success(masterPackages))
                return@addSnapshotListener
            }
        }
        awaitClose{}
    }
    override suspend fun editMasterPackage(masterPackage: MasterPackage): Result<Unit> {
       try {
           masterPackagesCollection.document(masterPackage.id).set(masterPackage).await()
           return Result.success(Unit)
       }catch (e: Exception){
           crashlytics.recordException(e)
           return Result.failure(e)
       }
    }

    override suspend fun deleteMasterPackage(masterPackageId: String): Result<Unit> {
        try {
            masterPackagesCollection.document(masterPackageId).delete().await()
            return Result.success(Unit)
        }catch (e: Exception){
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }

    override suspend fun getMasterPackageById(masterPackageId: String): Result<MasterPackage> {
        try {
            val document = masterPackagesCollection.document(masterPackageId).get().await()
            val masterPackage = document.toObject(MasterPackage::class.java)?: return Result.failure(Exception("MasterPackage not found"))
            return Result.success(masterPackage)
        }catch (e: Exception){
            crashlytics.recordException(e)
            return Result.failure(e)
        }
    }


}