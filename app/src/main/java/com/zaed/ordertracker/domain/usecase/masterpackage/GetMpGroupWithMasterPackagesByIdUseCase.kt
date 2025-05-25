package com.zaed.ordertracker.domain.usecase.masterpackage

import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMpGroupWithMasterPackagesByIdUseCase(private val repository: MpGroupRepository) {
    operator fun invoke(groupId: String): Flow<Result<MpGroup>> = flow {
        try {
            val masterPackageGroup = repository.getMpGroupById(groupId).getOrThrow()
            repository.getMasterPackagesByGroupId(groupId).collect { result ->
                result.fold(
                    onSuccess = { masterPackages ->
                        emit(Result.success(masterPackageGroup.copy(masterPackages = masterPackages)))
                    },
                    onFailure = {
                        emit(Result.failure(it))
                    }
                )
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}