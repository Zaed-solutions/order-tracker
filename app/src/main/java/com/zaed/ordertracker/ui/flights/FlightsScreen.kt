package com.zaed.ordertracker.ui.flights

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.ConfirmDeleteBottomSheet
import com.zaed.ordertracker.ui.flights.components.FlightsList
import com.zaed.ordertracker.ui.flights.components.SaveFlightBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlightsScreen(
    modifier: Modifier = Modifier,
    viewModel: FlightsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToFlightDetails: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FlightsScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                FlightsUiAction.NavigateBack -> onNavigateBack()
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.flights),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(FlightsUiAction.NavigateBack)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
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
        Box(modifier = Modifier.padding(innerPadding)) {
            FlightsList(
                flights = state.flights,
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
