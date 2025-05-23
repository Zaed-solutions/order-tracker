package com.zaed.ordertracker.domain.usecase

import com.zaed.ordertracker.domain.model.User
import com.zaed.ordertracker.domain.repository.UserRepository

class SaveUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User)= userRepository.saveUser(user)
}
