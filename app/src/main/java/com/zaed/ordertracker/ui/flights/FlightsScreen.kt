package com.zaed.ordertracker.ui.flights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.ConfirmDeleteBottomSheet
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.components.SearchBar
import com.zaed.ordertracker.ui.components.SearchBarWithEditIcon
import com.zaed.ordertracker.ui.flights.components.FlightsList
import com.zaed.ordertracker.ui.flights.components.SaveFlightBottomSheet
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
        if(state.isLoggedOut){
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
    var isSaveFlightSheetVisible by remember {
        mutableStateOf(false)
    }
    var isConfirmDeleteFlightSheetVisible by remember {
        mutableStateOf(false)
    }
    var selectedFlight: Flight by remember {
        mutableStateOf(Flight())
    }
    val snackbarHostState = remember { SnackbarHostState() }
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
                            //todo: open profile dialog
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
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
                                        icon = Icons.Default.Upload,
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedFlight = Flight()
                    isSaveFlightSheetVisible = true
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            SearchBarWithEditIcon(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                searchQuery = state.searchQuery,
                onUpdateSearchQuery = {
                    onAction(FlightsUiAction.UpdateSearchQuery(it))
                },
                onEditClicked = {
                    selectedFlight = Flight()
                    isSaveFlightSheetVisible = true
                },
            )
            FlightsList(
                flights = state.displayedFlights,
                onDeleteFlight = {
                    selectedFlight = it
                    isConfirmDeleteFlightSheetVisible = true
                },
                onEditFlight = {
                    selectedFlight = it
                    isSaveFlightSheetVisible = true
                },
                onFlightClicked = {
                    onAction(FlightsUiAction.NavigateToFlightDetails(it.id))
                },
            )
            SaveFlightBottomSheet(
                isVisible = isSaveFlightSheetVisible,
                initialFlight = selectedFlight,
                onDismiss = {
                    isSaveFlightSheetVisible = false
                },
                onSaveFlight = {
                    isSaveFlightSheetVisible = false
                    onAction(
                        if (it.id.isBlank()) {
                            FlightsUiAction.AddFlight(it)
                        } else {
                            FlightsUiAction.EditFlight(it)
                        },
                    )
                },
            )
            ConfirmDeleteBottomSheet(
                visible = isConfirmDeleteFlightSheetVisible,
                label = stringResource(R.string.flight),
                onDismiss = {
                    isConfirmDeleteFlightSheetVisible = false
                },
                onConfirm = {
                    isConfirmDeleteFlightSheetVisible = false
                    onAction(FlightsUiAction.DeleteFlight(selectedFlight.id))
                },
            )
        }
    }
}
