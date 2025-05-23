package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.repository.UserRepository

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUsers()
}
