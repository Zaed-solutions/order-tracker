package com.zaed.ordertracker.domain.usecase.authentication

import com.zaed.ordertracker.domain.model.User
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
        if (User.validatePassword(password).first) return Result.failure(InvalidPasswordException())
        if (User.validateUsername(username).first) return Result.failure(InvalidUsernameException())
        return repo.login(username, password)
    }
}
