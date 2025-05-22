package com.zaed.ordertracker.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.AddMasterPackageToGroupUseCase
import com.zaed.ordertracker.domain.usecase.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.GetMasterPackagesByFlightIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupByIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMpGroupBackgroundColorUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetFlightShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightDetailsViewModel(
    private val getFlightShipmentsUseCase: GetFlightShipmentsUseCase,
    private val createShipmentUseCase: CreateShipmentUseCase,
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase,

    private val getMpGroupsUseCase: GetMpGroupsUseCase,
    private val getMpGroupByIdUseCase: GetMpGroupByIdUseCase,
    private val addMasterPackageToGroupUseCase: AddMasterPackageToGroupUseCase,
    private val updateMasterPackageUseCase: UpdateMasterPackageUseCase,
    private val updateMpGroupBackgroundColorUseCase: UpdateMpGroupBackgroundColorUseCase,
    private val exportMasterPackagesUseCase: ExportMasterPackagesUseCase,
    private val addNewMasterPackage: AddMasterPackageUseCase,
    private val getMasterPackagesByFlightIdUseCase: GetMasterPackagesByFlightIdUseCase,
    private val deleteMasterPackageUseCase: DeleteMasterPackageUseCase,
    private val editMasterPackageUseCase: UpdateMasterPackageUseCase,
) : ViewModel() {
    private val TAG: String = "HomeViewModel"
    private val _uiState = MutableStateFlow(FlightDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun init(flightId: String) {
        Log.d(TAG, "init: flightId: $flightId")
        _uiState.update { it.copy(flightId = flightId) }
        fetchShipments(flightId)
        fetchMasterPackages()
    }

    private fun fetchMasterPackages() {
        viewModelScope.launch(Dispatchers.IO) {
            getMasterPackagesByFlightIdUseCase(uiState.value.flightId).collect { result ->
                result.onSuccess { masterPackages ->
                    _uiState.update { oldState ->
                        Log.d(TAG, "fetchMasterPackages: masterPackages: ${masterPackages.size}")
                        oldState.copy(masterPackages = masterPackages)
                    }
                }.onFailure {
                    Log.e(TAG, "fetchMasterPackages: ", it)
                }
            }
        }
    }

    private fun deleteMasterPackage(masterPackageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMasterPackageUseCase(masterPackageId).fold(
                onSuccess = {
                    Log.d(TAG, "deleteMasterPackage: success")
                },
                onFailure = {
                    Log.e(TAG, "deleteMasterPackage: ", it)
                }
            )
        }
    }

    private fun editMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            editMasterPackageUseCase(masterPackage).fold(
                onSuccess = {
                    Log.d(TAG, "editMasterPackage: success")
                },
                onFailure = {
                    Log.e(TAG, "editMasterPackage: ", it)
                }
            )
        }
    }

    private fun fetchShipments(flightId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFlightShipmentsUseCase.invoke(flightId).collect { result ->
                result
                    .onSuccess { shipments ->
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = false, shipments = shipments)
                        }
                    }.onFailure {
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = false)
                        }
                    }
            }
        }
    }

    fun handleAction(action: FlightDetailsUiAction) {
        when (action) {
            is FlightDetailsUiAction.AddShipment -> addShipment(action.shipment)
            is FlightDetailsUiAction.UpdateShipment -> updateShipment(action.updatedShipment)
            is FlightDetailsUiAction.DeleteShipment -> deleteShipment(action.shipmentId)
            is FlightDetailsUiAction.AddNewMasterPackage -> addNewMasterPackage(action.masterPackage)
            is FlightDetailsUiAction.DeleteMasterPackage -> deleteMasterPackage(action.masterPackageId)
            is FlightDetailsUiAction.EditMasterPackage -> editMasterPackage(action.masterPackage)

            else -> Unit
        }
    }

    private fun addNewMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewMasterPackage.invoke(masterPackage.copy(flightId = uiState.value.flightId))
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
            createShipmentUseCase(shipment.copy(flightId = uiState.value.flightId))
                .onSuccess {
                    Log.d(TAG, "addShipment: success")
                }.onFailure {
                    Log.e(TAG, "addShipment: ", it)
                }
        }
    }
}
