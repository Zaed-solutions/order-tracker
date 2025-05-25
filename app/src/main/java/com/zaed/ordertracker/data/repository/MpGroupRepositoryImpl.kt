package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.MasterPackageRemoteSource
import com.zaed.ordertracker.data.source.remote.MpGroupRemoteSource
import com.zaed.ordertracker.data.source.remote.ShipmentRemoteDataSource
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MpGroupRepositoryImpl(
    private val mpGroupDataSource: MpGroupRemoteSource,
    private val masterPackageDataSource: MasterPackageRemoteSource,
    private val shipmentDataSource: ShipmentRemoteDataSource,
) : MpGroupRepository {
    override suspend fun getMpGroups() = mpGroupDataSource.getMpGroups()

    override suspend fun saveMpGroup(mpGroup: MpGroup) = mpGroupDataSource.saveMpGroup(mpGroup)

    override suspend fun deleteMpGroup(mpGroupId: String) = mpGroupDataSource.deleteMpGroup(mpGroupId)

    override suspend fun updateMpGroupBackgroundColor(
        groupId: String,
        color: String,
    ): Result<Unit> = mpGroupDataSource.updateMpGroupBackgroundColor(groupId, color)

    override suspend fun getMasterPackagesByGroupId(groupId: String): Flow<Result<List<MasterPackage>>> =
        masterPackageDataSource.getMasterPackagesByGroupId(groupId).map {
            it.map { masterPackages ->
                masterPackages.map { masterPackage ->
                    masterPackage.copy(
                        exported =
                            shipmentDataSource
                                .doesMasterPackageHaveUnExportedShipments(
                                    masterPackage.id,
                                ).getOrDefault(false),
                    )
                }
            }
        }

    override suspend fun getMpGroupById(groupId: String): Result<MpGroup> = mpGroupDataSource.getMpGroupById(groupId)

    override suspend fun addMasterPackageToGroup(
        groupId: String,
        masterPackage: MasterPackage,
    ): Result<Unit> = mpGroupDataSource.addMasterPackageToGroup(groupId, masterPackage)

    override suspend fun addNewMasterPackage(masterPackage: MasterPackage): Result<Unit> =
        masterPackageDataSource.addNewMasterPackage(masterPackage)

    override suspend fun updateMasterPackage(masterPackage: MasterPackage): Result<Unit> =
        mpGroupDataSource.updateMasterPackage(masterPackage)

    override suspend fun exportMasterPackages(
        groupId: String,
        masterPackageIds: List<String>,
    ): Result<Unit> = mpGroupDataSource.exportMasterPackages(groupId, masterPackageIds)

    override suspend fun getMasterPackagesByFlightId(flightId: String): Flow<Result<List<MasterPackage>>> =
        masterPackageDataSource.getMasterPackagesByFlightId(flightId).map {
            it.map { masterPackages ->
                masterPackages.map { masterPackage ->
                    masterPackage.copy(
                        exported =
                            shipmentDataSource
                                .doesMasterPackageHaveUnExportedShipments(
                                    masterPackage.id,
                                ).getOrDefault(false),
                    )
                }
            }
        }

    override suspend fun editMasterPackage(masterPackage: MasterPackage): Result<Unit> =
        masterPackageDataSource.editMasterPackage(masterPackage)

    override suspend fun deleteMasterPackage(masterPackageId: String): Result<Unit> =
        masterPackageDataSource.deleteMasterPackage(masterPackageId)

    override suspend fun getMasterPackageById(masterPackageId: String): Result<MasterPackage> =
        masterPackageDataSource.getMasterPackageById(masterPackageId)
}
