package com.zaed.ordertracker.domain.usecase.masterpackage

import com.zaed.ordertracker.domain.repository.MpGroupRepository

class DeleteMpGroupUseCase(
    private val mpGroupRepository: MpGroupRepository
) {
    suspend operator fun invoke(id: String)  = mpGroupRepository.deleteMpGroup(id)
}
