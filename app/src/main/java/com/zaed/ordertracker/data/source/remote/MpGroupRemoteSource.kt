package com.zaed.ordertracker.data.source.remote

import com.zaed.ordertracker.domain.model.MpGroup
import kotlinx.coroutines.flow.Flow

interface MpGroupRemoteSource {
    // MP Group management
    suspend fun getMpGroups(): Flow<Result<List<MpGroup>>>
    suspend fun saveMpGroup(mpGroup: MpGroup): Result<Unit>
    suspend fun deleteMpGroup(mpGroupId: String): Result<Unit>
}