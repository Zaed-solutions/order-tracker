package com.zaed.ordertracker.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.GetMasterPackagesByFlightIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMasterPackageUseCase
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
    private val updateMasterPackageUseCase: UpdateMasterPackageUseCase,
    private val exportMasterPackagesUseCase: ExportMasterPackagesUseCase,
    private val addNewMasterPackage: AddMasterPackageUseCase,
    private val getMasterPackagesByFlightIdUseCase: GetMasterPackagesByFlightIdUseCase,
    private val deleteMasterPackageUseCase: DeleteMasterPackageUseCase,
    private val saveMpGroupUseCase: SaveMpGroupUseCase,
) : ViewModel() {
    private val TAG: String = "HomeViewModel"
    private val _uiState = MutableStateFlow(FlightDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun init(flightId: String) {
        Log.d(TAG, "init: flightId: $flightId")
        _uiState.update { it.copy(flightId = flightId) }
        fetchShipments(flightId)
        fetchMasterPackages()
        fetchGroups()
    }

    private fun fetchGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            getMpGroupsUseCase().collect { result ->
                result.onSuccess { groups ->
                    _uiState.update { oldState ->
                        Log.d(TAG, "fetchGroups: groups: ${groups.size}")
                        oldState.copy(groups = groups)
                    }
                }.onFailure {
                    Log.e(TAG, "fetchGroups: ", it)
                }
            }
        }
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
            updateMasterPackageUseCase(masterPackage).fold(
                onSuccess = {
                    Log.d(TAG, "editMasterPackage: success")
                    // Check if the master package should be added to a group
//                    checkAndAddToGroup(masterPackage)
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
            // First, add the master package to the database
            val masterPackageWithFlightId = masterPackage.copy(flightId = uiState.value.flightId)
            addNewMasterPackage.invoke(masterPackageWithFlightId)
//
//            // Then, check if it should be added to a group
//            checkAndAddToGroup(masterPackageWithFlightId)
        }
    }

//    private suspend fun checkAndAddToGroup(masterPackage: MasterPackage) {
//        // Extract the prefix from the master package name
//        // The prefix is the alphabetic part at the beginning of the name
//        val name = masterPackage.name
//        val prefixMatch = Regex("^([A-Za-z]+)\\d+.*$").find(name)
//
//        Log.d(TAG, "checkAndAddToGroup: masterPackage name: $name")
//
//        if (prefixMatch != null) {
//            val prefix = prefixMatch.groupValues[1]
//            Log.d(TAG, "checkAndAddToGroup: extracted prefix: $prefix")
//
//            // Check if there's already a group with this prefix
//            getMpGroupsUseCase().collect { result ->
//                result.onSuccess { groups ->
//                    // First, check if the master package is already in any group
//                    val groupContainingMasterPackage = groups.find { group ->
//                        group.masterPackages.any { it.id == masterPackage.id }
//                    }
//
//                    Log.d(TAG, "checkAndAddToGroup: groups count: ${groups.size}")
//                    Log.d(TAG, "checkAndAddToGroup: masterPackage already in group: ${groupContainingMasterPackage?.name}")
//
//                    // If the master package is already in a group with a different name,
//                    // remove it from that group
//                    if (groupContainingMasterPackage != null && groupContainingMasterPackage.name != prefix) {
//                        Log.d(TAG, "checkAndAddToGroup: removing masterPackage from group: ${groupContainingMasterPackage.name}")
//                        val updatedMasterPackages = groupContainingMasterPackage.masterPackages.filter { it.id != masterPackage.id }
//                        val updatedGroup = groupContainingMasterPackage.copy(masterPackages = updatedMasterPackages)
//                        saveMpGroupUseCase(updatedGroup)
//                    }
//
//                    // Look for a group with the same prefix
//                    val targetGroup = groups.find { it.name == prefix }
//
//                    Log.d(TAG, "checkAndAddToGroup: found target group with prefix $prefix: ${targetGroup != null}")
//
//                    if (targetGroup != null) {
//                        // Check if the master package is already in this group
//                        val alreadyInGroup = targetGroup.masterPackages.any { it.id == masterPackage.id }
//                        Log.d(TAG, "checkAndAddToGroup: masterPackage already in target group: $alreadyInGroup")
//
//                        if (!alreadyInGroup) {
//                            // Add the master package to the existing group
//                            Log.d(TAG, "checkAndAddToGroup: adding masterPackage to existing group: ${targetGroup.name}")
//                            val updatedMasterPackages = targetGroup.masterPackages.toMutableList()
//                            updatedMasterPackages.add(masterPackage)
//                            val updatedGroup = targetGroup.copy(masterPackages = updatedMasterPackages)
//                            saveMpGroupUseCase(updatedGroup)
//                        }
//                    } else {
//                        // Create a new group with the prefix as the name
//                        Log.d(TAG, "checkAndAddToGroup: creating new group with name: $prefix")
//                        val newGroup = MpGroup(
//                            name = prefix,
//                            masterPackages = listOf(masterPackage)
//                        )
//                        saveMpGroupUseCase(newGroup)
//                    }
//                }
//            }
//        }
//    }

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
