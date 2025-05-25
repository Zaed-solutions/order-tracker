package com.zaed.ordertracker.ui.flights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.Flight
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
    private val getAllFlightsUseCase: GetAllFlightsUseCase,
    private val createFlightUseCase: CreateFlightUseCase,
    private val updateFlightUseCase: UpdateFlightUseCase,
    private val deleteFlightUseCase: DeleteFlightUseCase,
) : ViewModel() {
    private val TAG: String = "FlightsViewModel"
    private val _uiState = MutableStateFlow(FlightsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFlights()
    }

    private fun fetchFlights() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFlightsUseCase().collect { result ->
                result
                    .onSuccess { flights ->
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = false, flights = flights)
                        }
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
            FlightsUiAction.ResetError -> _uiState.update { it.copy(error = null) }
            else -> Unit
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
                }.onFailure {
                    if(it is FlightHasUnprocessedShipmentsException){
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
                }.onFailure {
                    _uiState.update { oldState ->
                        oldState.copy(isLoading = false)
                    }
                    Log.e(TAG, "addFlight: ", it)
                }
        }
    }
}
