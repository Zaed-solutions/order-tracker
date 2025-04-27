package com.zaed.ordertracker.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: LoginUiAction) {
        when (action) {
            LoginUiAction.OnLoginClicked -> TODO()
            is LoginUiAction.OnUpdatePassword -> TODO()
            is LoginUiAction.OnUpdateUsername -> TODO()
            else -> Unit
        }
    }
}
