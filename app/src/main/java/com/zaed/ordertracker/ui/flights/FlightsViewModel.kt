package com.zaed.ordertracker.ui.flights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.domain.usecase.authentication.GetCurrentUserUseCase
import com.zaed.ordertracker.domain.usecase.authentication.LogoutUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.CreateFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.DeleteFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetAllFlightsUseCase
import com.zaed.ordertracker.domain.usecase.flight.UpdateFlightUseCase
import com.zaed.ordertracker.domain.utils.FlightHasUnprocessedShipmentsException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightsViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getAllFlightsUseCase: GetAllFlightsUseCase,
    private val createFlightUseCase: CreateFlightUseCase,
    private val updateFlightUseCase: UpdateFlightUseCase,
    private val deleteFlightUseCase: DeleteFlightUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {
    private val TAG: String = "FlightsViewModel"
    private val _uiState = MutableStateFlow(FlightsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFlights()
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentUserUseCase()
                .onSuccess { user ->
                    _uiState.update { oldState ->
                        oldState.copy(currentUser = user)
                    }
                }.onFailure {
                    Log.e(TAG, "fetchCurrentUser: ", it)
                }
        }
    }

    private fun fetchFlights() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFlightsUseCase().collect { result ->
                result
                    .onSuccess { flights ->
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = false, allFlights = flights)
                        }
                        filterFlights()
                        Log.d(TAG, "fetchFlights: $flights")
                    }.onFailure {
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = false)
                        }
                        Log.e(TAG, "fetchFlights: ", it)
                    }
            }
        }
    }

    fun handleAction(action: FlightsUiAction) {
        when (action) {
            is FlightsUiAction.AddFlight -> addFlight(action.flight)
            is FlightsUiAction.DeleteFlight -> deleteFlight(action.flightId)
            is FlightsUiAction.EditFlight -> updateFlight(action.updatedFlight)
            is FlightsUiAction.UpdateSearchQuery -> updateSearchQuery(action.query)
            FlightsUiAction.ResetError -> _uiState.update { it.copy(error = null) }
            FlightsUiAction.Logout -> logout()
            is FlightsUiAction.UpdateFlightsList -> {
                updateFlights(action.flights)
            }

            else -> Unit
        }
    }

    private fun updateFlights(flights: List<Flight>) {
        viewModelScope.launch(Dispatchers.Default) {
            Log.d(TAG, "updateFlights: $flights")
            uiState.value.displayedFlights.forEach { flight ->
                if (flights.none { flight.id == it.id }) {
                    deleteFlight(flight.id)
                } else {
                    val matchingFlight = flights.firstOrNull { it.id == flight.id }
                    matchingFlight?.let {
                        if (it != flight) {
                            updateFlight(flight)
                        }
                    }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUserUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoggedOut = true)
                    }
                }.onFailure {
                    Log.e(TAG, "logout: ", it)
                }
        }
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { oldState ->
            oldState.copy(searchQuery = query)
        }
        filterFlights()
    }

    private fun filterFlights() {
        viewModelScope.launch(Dispatchers.Default) {
            val (query, allFlights) =
                uiState.value.run {
                    searchQuery to allFlights
                }
            if (query.isBlank()) {
                _uiState.update { oldState ->
                    oldState.copy(displayedFlights = allFlights)
                }
            } else {
                allFlights
                    .filter {
                        it.name.contains(query, ignoreCase = true)
                    }.let { filteredFlights ->
                        _uiState.update { oldState ->
                            oldState.copy(displayedFlights = filteredFlights)
                        }
                    }
            }
        }
    }

    private fun updateFlight(updatedFlight: Flight) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = false) }
            updateFlightUseCase(updatedFlight)
                .onSuccess {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                }.onFailure {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.e(TAG, "updateFlight: ", it)
                }
        }
    }

    private fun deleteFlight(flightId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            deleteFlightUseCase(flightId)
                .onSuccess {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.d(TAG, "deleteFlight: success: $flightId")
                }.onFailure {
                    if (it is FlightHasUnprocessedShipmentsException) {
                        _uiState.update { oldState ->
                            oldState.copy(error = "Flight has unprocessed shipments")
                        }
                    }
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.e(TAG, "deleteFlight: ", it)
                }
        }
    }

    private fun addFlight(flight: Flight) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            createFlightUseCase(flight)
                .onSuccess {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.d(TAG, "addFlight: Flight added successfully")
                }.onFailure {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.e(TAG, "addFlight: ", it)
                }
        }
    }
}
