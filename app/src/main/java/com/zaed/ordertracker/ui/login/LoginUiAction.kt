package com.zaed.ordertracker.ui.login

sealed interface LoginUiAction {
    data class OnUpdateUsername(
        val username: String,
    ) : LoginUiAction

    data object OnLoginClicked : LoginUiAction

    data class OnUpdatePassword(
        val password: String,
    ) : LoginUiAction
}
