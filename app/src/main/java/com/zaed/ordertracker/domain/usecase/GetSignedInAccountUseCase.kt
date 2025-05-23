package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.GoogleDriveRepository

class GetSignedInAccountUseCase(
    private val googleDriveRepository: GoogleDriveRepository
) {
    suspend operator fun invoke() = googleDriveRepository.getSignedInAccount()

}