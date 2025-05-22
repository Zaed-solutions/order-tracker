package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository

class GetMpGroupByIdUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(groupId: String): Result<MpGroup> {
        return repository.getMpGroupById(groupId)
    }
}