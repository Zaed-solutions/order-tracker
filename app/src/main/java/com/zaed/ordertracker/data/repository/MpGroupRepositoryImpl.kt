package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.MasterPackageRemoteSource
import com.zaed.ordertracker.data.source.remote.MpGroupRemoteSource
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import kotlinx.coroutines.flow.Flow

class MpGroupRepositoryImpl(
    private val mpGroupDataSource: MpGroupRemoteSource,
    private val masterPackageDataSource: MasterPackageRemoteSource
) : MpGroupRepository {
    override suspend fun getMpGroups() = mpGroupDataSource.getMpGroups()

    override suspend fun saveMpGroup(mpGroup: MpGroup) = mpGroupDataSource.saveMpGroup(mpGroup)

    override suspend fun deleteMpGroup(mpGroupId: String) = mpGroupDataSource.deleteMpGroup(mpGroupId)
    override suspend fun updateMpGroupBackgroundColor(
        groupId: String,
        color: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getMpGroupById(groupId: String): Result<MpGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun addMasterPackageToGroup(
        groupId: String,
        masterPackage: MasterPackage
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewMasterPackage(masterPackage: MasterPackage): Result<Unit>  = masterPackageDataSource.addNewMasterPackage(masterPackage)

    override suspend fun updateMasterPackage(masterPackage: MasterPackage): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun exportMasterPackages(
        groupId: String,
        masterPackageIds: List<String>
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getMasterPackagesByFlightId(flightId: String): Flow<Result<List<MasterPackage>>> {
       return masterPackageDataSource.getMasterPackagesByFlightId(flightId)
    }

    override suspend fun editMasterPackage(masterPackage: MasterPackage): Result<Unit> {
        return masterPackageDataSource.editMasterPackage(masterPackage)
    }

    override suspend fun deleteMasterPackage(masterPackageId: String): Result<Unit> {
        return masterPackageDataSource.deleteMasterPackage(masterPackageId)
    }
}