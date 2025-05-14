package com.zaed.ordertracker.ui.login

data class LoginUiState(
    val username: String = "",
    val isUsernameError: Boolean = false,
    val password: String = "",
    val isPasswordError: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val errorMessage: String? = null,
)
