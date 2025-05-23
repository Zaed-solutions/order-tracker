package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.FirebaseCredentialRepository

class GetFirebaseCredentialUseCase(
    private val firebaseCredentialRepository: FirebaseCredentialRepository
) {
    suspend operator fun invoke() = firebaseCredentialRepository.getFirebaseCredential()
}
