package com.zaed.ordertracker.domain.usecase.authentication

import com.zaed.ordertracker.domain.repository.AuthenticationRepository

class LogoutUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = authenticationRepository.logout()
}