package com.zaed.ordertracker.ui.flightdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.masterpackage.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMasterPackagesByFlightIdUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.UpdateMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.UploadExcelSheetUseCase
import com.zaed.ordertracker.domain.usecase.authentication.GetCurrentUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetFlightByIdUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetFlightShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class FlightDetailsViewModel(
    private val getFlightShipmentsUseCase: GetFlightShipmentsUseCase,
    private val createShipmentUseCase: CreateShipmentUseCase,
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase,
    private val getMpGroupsUseCase: GetMpGroupsUseCase,
    private val updateMasterPackageUseCase: UpdateMasterPackageUseCase,
    private val getFlightById: GetFlightByIdUseCase,
    private val exportMasterPackagesUseCase: ExportMasterPackagesUseCase,
    private val addNewMasterPackage: AddMasterPackageUseCase,
    private val getMasterPackagesByFlightIdUseCase: GetMasterPackagesByFlightIdUseCase,
    private val deleteMasterPackageUseCase: DeleteMasterPackageUseCase,
    private val saveMpGroupUseCase: SaveMpGroupUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val uploadExcelSheetUseCase: UploadExcelSheetUseCase
) : ViewModel() {
    private val TAG: String = "HomeViewModel"
    private val _uiState = MutableStateFlow(FlightDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun init(flightId: String) {
        Log.d(TAG, "init: flightId: $flightId")
        fetchCurrentUser()
        fetchFlight(flightId)
        fetchShipments(flightId)
        fetchMasterPackages()
        fetchGroups()
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

    private fun fetchFlight(flightId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFlightById(flightId)
                .onSuccess { flight ->
                    _uiState.update { oldState ->
                        oldState.copy(flight = flight)
                    }
                }.onFailure {
                    Log.e(TAG, "fetchFlight: ", it)
                }
        }
    }

    private fun fetchGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            getMpGroupsUseCase().collect { result ->
                result
                    .onSuccess { groups ->
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
            getMasterPackagesByFlightIdUseCase(uiState.value.flight.id).collect { result ->
                result
                    .onSuccess { masterPackages ->
                        _uiState.update { oldState ->
                            Log.d(
                                TAG,
                                "fetchMasterPackages: masterPackages: ${masterPackages.size}",
                            )
                            oldState.copy(masterPackages = masterPackages.filter { it.groupId.isEmpty() })
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
                },
            )
        }
    }

    private fun editMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            val namePrefix = masterPackage.name.takeWhile { it.isLetter() }
            val sameMasterPackage =
                uiState.value.masterPackages.filter {
                    it.name.startsWith(namePrefix) && it.id != masterPackage.id
                }
            val existingGroup = uiState.value.groups.find { it.name == namePrefix }

            val updatedMasterPackage =
                masterPackage.apply {
                    if (sameMasterPackage.isNotEmpty() && existingGroup == null) {
                        val groupId =
                            saveMpGroupUseCase
                                .invoke(
                                    MpGroup(
                                        name = namePrefix,
                                        color = 0x00000,
                                    ),
                                ).getOrElse { "" }

                        this.groupId = groupId
                        flightId = uiState.value.flight.id
                        sameMasterPackage.forEach { mp ->
                            updateMasterPackageUseCase(
                                mp.copy(
                                    flightId = uiState.value.flight.id,
                                    groupId = groupId,
                                ),
                            )
                        }
                    } else if (existingGroup != null) {
                        this.groupId = existingGroup.id
                        flightId = uiState.value.flight.id
                        sameMasterPackage.forEach { mp ->
                            updateMasterPackageUseCase(
                                mp.copy(
                                    flightId = uiState.value.flight.id,
                                    groupId = existingGroup.id,
                                ),
                            )
                        }
                    } else {
                        groupId = ""
                        flightId = uiState.value.flight.id
                    }
                }
            updateMasterPackageUseCase(updatedMasterPackage).fold(
                onSuccess = {
                    Log.d(TAG, "editMasterPackage: success")
                },
                onFailure = {
                    Log.e(TAG, "editMasterPackage: ", it)
                },
            )
        }
    }

    private fun fetchShipments(flightId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFlightShipmentsUseCase.invoke(flightId).collect { result ->
                result
                    .onSuccess { shipments ->
                        val sortedShipments = shipments.sortedBy { it.addedAt }
                        _uiState.update { oldState ->
                            oldState.copy(
                                isLoading = false,
                                allShipments = sortedShipments,
                                displayShipments = sortedShipments.filter { it.masterPackageId.isBlank() },
                            )
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
            is FlightDetailsUiAction.UploadExportedShipments -> uploadExportedShipments(action.excelSheet)
            else -> Unit
        }
    }

    private fun uploadExportedShipments(excelSheet: File) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadExcelSheetUseCase(
                flightId = uiState.value.flight.id,
                file = excelSheet
            ).onSuccess {
                Log.d(TAG, "uploadExportedShipments: success $it")
            }.onFailure {
                //todo if user not found error display navigate to settings exception
                Log.e(TAG, "uploadExportedShipments: failure", it)
            }
        }
    }

    private fun addNewMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            val namePrefix = masterPackage.name.takeWhile { it.isLetter() }
            val sameMasterPackage =
                uiState.value.masterPackages.filter {
                    it.name.startsWith(namePrefix)
                }
            val existingGroup = uiState.value.groups.find { it.name == namePrefix }

            val updatedMasterPackage =
                masterPackage.apply {
                    if (sameMasterPackage.isNotEmpty() && existingGroup == null) {
                        val groupId =
                            saveMpGroupUseCase
                                .invoke(
                                    MpGroup(
                                        name = namePrefix,
                                        color = 0x00000,
                                    ),
                                ).getOrElse { "" }

                        this.groupId = groupId
                        flightId = uiState.value.flight.id
                        sameMasterPackage.forEach { mp ->
                            updateMasterPackageUseCase(
                                mp.copy(
                                    flightId = uiState.value.flight.id,
                                    groupId = groupId,
                                ),
                            )
                        }
                    } else if (existingGroup != null) {
                        this.groupId = existingGroup.id
                        flightId = uiState.value.flight.id
                        sameMasterPackage.forEach { mp ->
                            updateMasterPackageUseCase(
                                mp.copy(
                                    flightId = uiState.value.flight.id,
                                    groupId = existingGroup.id,
                                ),
                            )
                        }
                    } else {
                        groupId = ""
                        flightId = uiState.value.flight.id
                    }
                }
            addNewMasterPackage.invoke(updatedMasterPackage)
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
            val updatedShipment =
                shipment.copy(
                    flightId = uiState.value.flight.id,
                    addedById = uiState.value.currentUser.id,
                    addedByName = uiState.value.currentUser.username,
                )
            createShipmentUseCase(updatedShipment)
                .onSuccess {
                    Log.d(TAG, "addShipment: success")
                }.onFailure {
                    Log.e(TAG, "addShipment: ", it)
                }
        }
    }
}
