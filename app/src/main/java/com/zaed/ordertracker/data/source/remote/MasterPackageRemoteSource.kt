package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.MasterPackage
import kotlinx.coroutines.flow.Flow

interface MasterPackageRemoteSource {
    suspend fun addNewMasterPackage(masterPackage: MasterPackage): Result<Unit>
    fun getMasterPackagesByFlightId(flightId: String): Flow<Result<List<MasterPackage>>>
    suspend fun editMasterPackage(masterPackage: MasterPackage): Result<Unit>
    suspend fun deleteMasterPackage(masterPackageId: String): Result<Unit>
    suspend fun getMasterPackageById(masterPackageId: String): Result<MasterPackage>
}
