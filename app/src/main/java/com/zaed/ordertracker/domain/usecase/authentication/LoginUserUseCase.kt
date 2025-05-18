package com.zaed.ordertracker.domain.usecase.authentication

import com.zaed.ordertracker.domain.repository.AuthenticationRepository
import com.zaed.ordertracker.domain.utils.InvalidPasswordException
import com.zaed.ordertracker.domain.utils.InvalidUsernameException
import com.zaed.ordertracker.domain.utils.hashWithMD5

class LoginUserUseCase(
    private val repo: AuthenticationRepository,
) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ): Result<Unit> {
        if (!isValidPassword(password)) return Result.failure(InvalidPasswordException())
        if (!isValidUsername(username)) return Result.failure(InvalidUsernameException())
        val hashedPassword = hashWithMD5(password)
        return repo.login(username, hashedPassword)
    }

    private fun isValidUsername(username: String): Boolean = username.isNotBlank() && username.length >= 3

    private fun isValidPassword(password: String): Boolean = password.length >= 8
}
