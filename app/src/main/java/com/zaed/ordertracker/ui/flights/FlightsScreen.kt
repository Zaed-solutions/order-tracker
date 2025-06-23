package com.zaed.ordertracker.ui.flights

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.components.SearchBar
import com.zaed.ordertracker.ui.components.ViewProfileDialog
import com.zaed.ordertracker.ui.flights.components.AddFlightDialog
import com.zaed.ordertracker.ui.flights.components.FlightsList
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlightsScreen(
    modifier: Modifier = Modifier,
    viewModel: FlightsViewModel = koinViewModel(),
    onNavigateToSettings: () -> Unit,
    onNavigateToFlightDetails: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            onNavigateToLogin()
        }
    }
    FlightsScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                FlightsUiAction.NavigateToSettings -> onNavigateToSettings()
                is FlightsUiAction.NavigateToFlightDetails -> onNavigateToFlightDetails(action.flightId)
                else -> {
                    viewModel.handleAction(action)
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsScreenContent(
    modifier: Modifier = Modifier,
    state: FlightsUiState,
    onAction: (FlightsUiAction) -> Unit,
) {
    var isAddFlightDialogVisible by remember {
        mutableStateOf(false)
    }
    var isEditProfileDialogVisible by remember {
        mutableStateOf(false)
    }
    var isEditMode by remember {
        mutableStateOf(false)
    }
    var updatedFlights by remember { mutableStateOf(state.displayedFlights) }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.displayedFlights) {
        updatedFlights = state.displayedFlights
    }
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
            )
            onAction(FlightsUiAction.ResetError)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        isAddFlightDialogVisible = true
                    },
                    modifier = Modifier.size(height = 70.dp, width = 290.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF942E2E),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(42.dp),
                    )
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.flights),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            isEditProfileDialogVisible = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                        )
                    }
                    MoreDropDownMenu(
                        modifier = Modifier.padding(end = 8.dp),
                        items =
                            buildList {
                                add(
                                    MoreDropdownItem(
                                        title = stringResource(R.string.settings),
                                        onClick = {
                                            onAction(FlightsUiAction.NavigateToSettings)
                                        },
                                        icon = Icons.Default.Settings,
                                        tint = MaterialTheme.colorScheme.primary,
                                    ),
                                )
                                add(
                                    MoreDropdownItem(
                                        title = stringResource(R.string.logout),
                                        onClick = {
                                            onAction(FlightsUiAction.Logout)
                                        },
                                        icon = Icons.AutoMirrored.Filled.Logout,
                                        tint = MaterialTheme.colorScheme.error,
                                    ),
                                )
                            },
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Row(
                modifier = modifier.padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SearchBar(
                    modifier = Modifier.weight(1f),
                    query = state.searchQuery,
                    onQueryChanged = {
                        onAction(FlightsUiAction.UpdateSearchQuery(it))
                    },
                )
                if (isEditMode) {
                    Button(
                        onClick = {
                            onAction(FlightsUiAction.UpdateFlightsList(updatedFlights))
                            isEditMode = false
                        },
                    ) {
                        Text(
                            text = "Save",
                        )
                    }
                    OutlinedIconButton(
                        onClick = {
                            isEditMode = false
                            updatedFlights = state.displayedFlights
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            isEditMode = true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = null,
                        )
                    }
                }
            }
            FlightsList(
                flights = updatedFlights,
                isEditMode = isEditMode,
                onUpdateFlights = {
                    updatedFlights = it
                },
                onFlightClicked = {
                    onAction(FlightsUiAction.NavigateToFlightDetails(it.id))
                },
            )
            AddFlightDialog(
                isVisible = isAddFlightDialogVisible,
                onAddFlight = { flight ->
                    onAction(FlightsUiAction.AddFlight(flight))
                    isAddFlightDialogVisible = false
                },
                onDismiss = {
                    isAddFlightDialogVisible = false
                },
            )
            AnimatedVisibility(isEditProfileDialogVisible) {
                ViewProfileDialog(
                    initialUser = state.currentUser,
                    onDismissRequest = {
                        isEditProfileDialogVisible = false
                    },
                )
            }
        }
    }
}
