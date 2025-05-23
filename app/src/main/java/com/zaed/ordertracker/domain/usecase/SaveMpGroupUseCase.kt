package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository

class SaveMpGroupUseCase(
    private val mpGroupRepository: MpGroupRepository
) {
    suspend operator fun invoke(mpGroup: MpGroup) = mpGroupRepository.saveMpGroup(mpGroup)
}
