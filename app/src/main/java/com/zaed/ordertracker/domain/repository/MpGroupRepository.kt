package com.zaed.ordertracker.domain.repository

import com.zaed.ordertracker.domain.model.MpGroup
import kotlinx.coroutines.flow.Flow

interface MpGroupRepository {
    suspend fun getMpGroups(): Flow<Result<List<MpGroup>>>
    suspend fun saveMpGroup(mpGroup: MpGroup): Result<Unit>
    suspend fun deleteMpGroup(mpGroupId: String): Result<Unit>
}