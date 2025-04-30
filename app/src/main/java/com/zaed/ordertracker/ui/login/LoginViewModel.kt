package com.zaed.ordertracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.usecase.LoginUserUseCase
import com.zaed.ordertracker.domain.utils.InvalidPasswordException
import com.zaed.ordertracker.domain.utils.InvalidUsernameException
import com.zaed.ordertracker.domain.utils.UserNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: LoginUiAction) {
        when (action) {
            LoginUiAction.OnLoginClicked -> onLogin()
            is LoginUiAction.OnUpdatePassword -> updatePassword(action.password)
            is LoginUiAction.OnUpdateUsername -> updateUsername(action.username)
            else -> Unit
        }
    }

    private fun updateUsername(username: String) {
        viewModelScope.launch {
            _uiState.update { oldState ->
                oldState.copy(
                    username = username,
                    isUsernameError = false,
                    errorMessage = null,
                )
            }
        }
    }

    private fun updatePassword(password: String) {
        viewModelScope.launch {
            _uiState.update { oldState ->
                oldState.copy(
                    password = password,
                    isPasswordError = false,
                    errorMessage = null,
                )
            }
        }
    }

    private fun onLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            loginUserUseCase(
                username = uiState.value.username,
                password = uiState.value.password,
            ).onSuccess {
                _uiState.value =
                    uiState.value.copy(
                        errorMessage = null,
                        isPasswordError = false,
                        isUsernameError = false,
                        isLoginSuccess = true,
                    )
            }.onFailure { exception ->
                when (exception) {
                    is InvalidUsernameException -> {
                        _uiState.update { oldState ->
                            oldState.copy(
                                isPasswordError = false,
                                isUsernameError = true,
                                errorMessage = "Invalid username",
                                isLoginSuccess = false,
                            )
                        }
                    }

                    is InvalidPasswordException -> {
                        _uiState.update { oldState ->
                            oldState.copy(
                                isPasswordError = true,
                                isUsernameError = false,
                                errorMessage = "Invalid password",
                                isLoginSuccess = false,
                            )
                        }
                    }

                    is UserNotFoundException -> {
                        _uiState.update { oldState ->
                            oldState.copy(
                                isPasswordError = false,
                                isUsernameError = false,
                                errorMessage = "User not found",
                                isLoginSuccess = false,
                            )
                        }
                    }

                    else -> {
                        _uiState.update { oldState ->
                            oldState.copy(
                                isPasswordError = false,
                                isUsernameError = false,
                                errorMessage = exception.message,
                                isLoginSuccess = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
