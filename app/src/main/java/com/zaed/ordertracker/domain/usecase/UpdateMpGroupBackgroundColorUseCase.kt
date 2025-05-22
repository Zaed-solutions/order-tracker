package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.MpGroupRepository

class UpdateMpGroupBackgroundColorUseCase(private val repository: MpGroupRepository) {
    suspend operator fun invoke(groupId: String, color: String): Result<Unit> {
        return repository.updateMpGroupBackgroundColor(groupId, color)
    }
}