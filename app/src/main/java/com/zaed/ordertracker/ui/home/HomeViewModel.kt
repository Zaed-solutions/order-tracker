package com.zaed.ordertracker.ui.home

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.AddMasterPackageToGroupUseCase
import com.zaed.ordertracker.domain.usecase.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupByIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMpGroupBackgroundColorUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetAllShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllShipmentsUseCase: GetAllShipmentsUseCase,
    private val createShipmentUseCase: CreateShipmentUseCase,
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase,

    private val getMpGroupsUseCase: GetMpGroupsUseCase,
    private val getMpGroupByIdUseCase: GetMpGroupByIdUseCase,
    private val addMasterPackageToGroupUseCase: AddMasterPackageToGroupUseCase,
    private val updateMasterPackageUseCase: UpdateMasterPackageUseCase,
    private val updateMpGroupBackgroundColorUseCase: UpdateMpGroupBackgroundColorUseCase,
    private val exportMasterPackagesUseCase: ExportMasterPackagesUseCase
) : ViewModel() {
    private val TAG: String = "HomeViewModel"
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        fetchShipments()
        loadGroups()
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

            is HomeUiAction.LoadGroups -> loadGroups()
            is HomeUiAction.SelectGroup -> selectGroup(action.groupId)
            is HomeUiAction.AddMasterPackage -> addMasterPackage(
                action.groupId,
                action.name,
                action.count,
                action.type
            )

            is HomeUiAction.UpdateMasterPackage -> updateMasterPackage(action.masterPackage)


            is HomeUiAction.ExportMasterPackages -> exportMasterPackages(
                action.groupId,
                action.masterPackageIds
            )
            else -> {}
        }
    }
    private fun exportMasterPackages(groupId: String, masterPackageIds: List<String>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            exportMasterPackagesUseCase(groupId, masterPackageIds).onSuccess {
                // Refresh the group to get the updated data
                selectGroup(groupId)
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to export Master Packages"
                    )
                }
            }
        }
    }

    private fun loadGroups() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getMpGroupsUseCase().collect { result ->
                result.onSuccess { groups ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            groups = groups
                        )
                    }
                }.onFailure { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load MP Groups"
                        )
                    }
                }
            }
        }
    }
    private fun selectGroup(groupId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getMpGroupByIdUseCase(groupId).onSuccess { group ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        selectedGroupId = groupId,
                        groups = state.groups.map {
                            if (it.id == groupId) group else it
                        }
                    )
                }
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load MP Group details"
                    )
                }
            }
        }
    }
    private fun addMasterPackage(groupId: String, name: String, count: Int, type: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val masterPackage = MasterPackage(
                id = "",
                name = name,
                count = count,
                type = type,
            )

            addMasterPackageToGroupUseCase(groupId, masterPackage).onSuccess {
                // Refresh the group to get the updated data
                selectGroup(groupId)
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to add Master Package"
                    )
                }
            }
        }
    }
    private fun updateMasterPackage(masterPackageUi: MasterPackage) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }


            updateMasterPackageUseCase(masterPackageUi).onSuccess {
                // Refresh the groups to get the updated data
                loadGroups()
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to update Master Package"
                    )
                }
            }
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
