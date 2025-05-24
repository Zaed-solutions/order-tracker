package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import kotlinx.coroutines.flow.Flow

interface MpGroupRemoteSource {
    // MP Group management
    suspend fun getMpGroups(): Flow<Result<List<MpGroup>>>
    suspend fun saveMpGroup(mpGroup: MpGroup): Result<String>
    suspend fun deleteMpGroup(mpGroupId: String): Result<Unit>
    suspend fun getMpGroupById(groupId: String): Result<MpGroup>
    suspend fun addMasterPackageToGroup(groupId: String, masterPackageDto: MasterPackage): Result<Unit>
    suspend fun updateMasterPackage(masterPackageDto: MasterPackage): Result<Unit>
    suspend fun updateMpGroupBackgroundColor(groupId: String, color: String): Result<Unit>
    suspend fun exportMasterPackages(groupId: String, masterPackageIds: List<String>): Result<Unit>
}