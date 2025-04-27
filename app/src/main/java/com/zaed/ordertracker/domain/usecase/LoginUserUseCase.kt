package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.AuthenticationRepository

class LoginUserUseCase(
    private val repo: AuthenticationRepository,
) {
    suspend operator fun invoke() {
        TODO()
    }
}
