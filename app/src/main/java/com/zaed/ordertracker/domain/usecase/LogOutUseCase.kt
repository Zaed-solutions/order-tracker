package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.GoogleDriveRepository

class LogOutUseCase(
    private val googleDriveRepository: GoogleDriveRepository
) {
    suspend operator fun invoke() = googleDriveRepository.signOut()
}
