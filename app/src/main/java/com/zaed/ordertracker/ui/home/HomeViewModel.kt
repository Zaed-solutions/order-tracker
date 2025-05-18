package com.zaed.ordertracker.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetAllShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllShipmentsUseCase: GetAllShipmentsUseCase,
    private val createShipmentUseCase: CreateShipmentUseCase,
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase,
) : ViewModel() {
    private val TAG: String = "HomeViewModel"
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchShipments()
    }

    private fun fetchShipments() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllShipmentsUseCase().collect { result ->
                result
                    .onSuccess { shipments ->
                        _uiState.value = HomeUiState(shipments = shipments)
                    }.onFailure {
                        _uiState.value = HomeUiState(isLoading = false)
                    }
            }
        }
    }

    fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.AddShipment -> addShipment(action.shipment)
            is HomeUiAction.UpdateShipment -> updateShipment(action.updatedShipment)
            is HomeUiAction.DeleteShipment -> deleteShipment(action.shipmentId)
        }
    }

    private fun deleteShipment(shipmentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteShipmentUseCase(shipmentId)
                .onSuccess {
                    Log.d(TAG, "deleteShipment: success")
                }.onFailure {
                    Log.e(TAG, "deleteShipment: ", it)
                }
        }
    }

    private fun updateShipment(updatedShipment: Shipment) {
        viewModelScope.launch(Dispatchers.IO) {
            updateShipmentUseCase(updatedShipment)
                .onSuccess {
                    Log.d(TAG, "updateShipment: success")
                }.onFailure {
                    Log.e(TAG, "updateShipment: ", it)
                }
        }
    }

    private fun addShipment(shipment: Shipment) {
        viewModelScope.launch(Dispatchers.IO) {
            createShipmentUseCase(shipment)
                .onSuccess {
                    Log.d(TAG, "addShipment: success")
                }.onFailure {
                    Log.e(TAG, "addShipment: ", it)
                }
        }
    }
}
