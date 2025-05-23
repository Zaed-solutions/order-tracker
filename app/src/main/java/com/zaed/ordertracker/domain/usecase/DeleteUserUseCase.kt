package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.UserRepository

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String) = userRepository.deleteUser(userId)
}
