package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.MpGroupRepository

class GetMpGroupsUseCase(
    private val mpGroupRepository: MpGroupRepository
) {
    suspend operator fun invoke() = mpGroupRepository.getMpGroups()
}
