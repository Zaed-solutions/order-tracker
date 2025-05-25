package com.zaed.ordertracker.domain.usecase.authentication

import com.zaed.ordertracker.domain.repository.AuthenticationRepository

class GetCurrentUserUseCase(
    private val authRepo: AuthenticationRepository,
) {
    suspend operator fun invoke() = authRepo.getCurrentUser()
}
