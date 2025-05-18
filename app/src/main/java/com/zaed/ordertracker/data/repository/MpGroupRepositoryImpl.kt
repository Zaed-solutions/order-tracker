package com.zaed.ordertracker.data.repository

import com.zaed.ordertracker.data.source.remote.MpGroupRemoteSource
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import kotlinx.coroutines.flow.Flow

class MpGroupRepositoryImpl(
    private val mpGroupDataSource: MpGroupRemoteSource
) : MpGroupRepository {
    override suspend fun getMpGroups() = mpGroupDataSource.getMpGroups()

    override suspend fun saveMpGroup(mpGroup: MpGroup) = mpGroupDataSource.saveMpGroup(mpGroup)

    override suspend fun deleteMpGroup(mpGroupId: String) = mpGroupDataSource.deleteMpGroup(mpGroupId)
}