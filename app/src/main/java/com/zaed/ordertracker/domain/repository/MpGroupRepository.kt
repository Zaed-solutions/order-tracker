package com.zaed.ordertracker.domain.repository

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import kotlinx.coroutines.flow.Flow

interface MpGroupRepository {
    suspend fun getMpGroups(): Flow<Result<List<MpGroup>>>
    suspend fun saveMpGroup(mpGroup: MpGroup): Result<Unit>
    suspend fun deleteMpGroup(mpGroupId: String): Result<Unit>
    suspend fun updateMpGroupBackgroundColor(groupId: String, color: String): Result<Unit>
    suspend fun getMpGroupById(groupId: String): Result<MpGroup>

    suspend fun addMasterPackageToGroup(groupId: String, masterPackage: MasterPackage): Result<Unit>
    suspend fun addNewMasterPackage(masterPackage: MasterPackage): Result<Unit>
    suspend fun updateMasterPackage(masterPackage: MasterPackage): Result<Unit>
    suspend fun exportMasterPackages(groupId: String, masterPackageIds: List<String>): Result<Unit>
    fun getMasterPackagesByFlightId(flightId: String): Flow<Result<List<MasterPackage>>>
    suspend fun editMasterPackage(masterPackage: MasterPackage): Result<Unit>
    suspend fun deleteMasterPackage(masterPackageId: String): Result<Unit>
}