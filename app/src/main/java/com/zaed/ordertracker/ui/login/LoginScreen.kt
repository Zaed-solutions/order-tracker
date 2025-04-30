package com.zaed.ordertracker.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.ui.components.PasswordTextField
import com.zaed.ordertracker.ui.components.TextInputTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.isLoginSuccess) {
        if (state.isLoginSuccess) {
            onNavigateToHome()
        }
    }
    LoginScreenContent(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                else -> viewModel.handleAction(action)
            }
        },
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onAction: (LoginUiAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surfaceVariant).padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier.sizeIn(maxWidth = 300.dp, maxHeight = 500.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // header logo and title
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayMedium,
                    )
                    // username
                    TextInputTextField(
                        value = state.username,
                        isError = state.isUsernameError,
                        errorMessage = R.string.invalid_username,
                        imageVector = Icons.Default.AccountCircle,
                        placeHolder = stringResource(R.string.username),
                        withBorder = true,
                        onValueChange = { username ->
                            onAction(LoginUiAction.OnUpdateUsername(username))
                        },
                    )
                    // password
                    PasswordTextField(
                        value = state.password,
                        isError = state.isPasswordError,
                        errorMessage = R.string.invalid_password,
                        placeHolder = stringResource(R.string.password),
                        onValueChange = { password ->
                            onAction(LoginUiAction.OnUpdateUsername(password))
                        },
                    )
                    // login button
                    Button(
                        modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp),
                        onClick = {
                            onAction(LoginUiAction.OnLoginClicked)
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                        )
                    }
                }
            }
        }
    }
}
