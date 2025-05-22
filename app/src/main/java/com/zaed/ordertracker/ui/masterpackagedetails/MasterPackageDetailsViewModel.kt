package com.zaed.ordertracker.ui.masterpackagedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.domain.usecase.EditMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.GetMasterPackageWithShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MasterPackageDetailsViewModel(
    private val getMasterPackageWithShipmentsUseCase: GetMasterPackageWithShipmentsUseCase,
    private val addMasterPackageShipmentUseCase: CreateShipmentUseCase,
    private val editMasterPackageShipmentUseCase: UpdateShipmentUseCase,
    private val editMasterPackageUseCase: EditMasterPackageUseCase,
    private val deleteMasterPackageShipmentUseCase: DeleteShipmentUseCase,
) : ViewModel() {
    private val TAG = "MasterPackageDetailsViewModel"
    private val _uiState = MutableStateFlow(MasterPackageDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun init(masterPackageId: String) {
        fetchMasterPackageWithShipments(masterPackageId)
    }

    private fun fetchMasterPackageWithShipments(masterPackageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMasterPackageWithShipmentsUseCase(masterPackageId).collect { result ->
                result.fold(
                    onSuccess = { masterPackage ->
                        _uiState.value = _uiState.value.copy(masterPackage = masterPackage)
                    },
                    onFailure = {
                        Log.d(
                            TAG,
                            "${MasterPackageDetailsViewModel::fetchMasterPackageWithShipments.name}: error: ${it.message}"
                        )
                    }
                )
            }
        }
    }

    fun onAction(action: MasterPackageDetailsUiAction) {
        when (action) {
            is MasterPackageDetailsUiAction.OnAddNewShipment -> addNewShipment(action.shipment)
            is MasterPackageDetailsUiAction.OnDeleteShipment -> deleteShipment(action.shipmentId)
            is MasterPackageDetailsUiAction.OnEditShipment -> updateShipment(action.shipment)
            is MasterPackageDetailsUiAction.OnEditMasterPackage -> updateMasterPackage(action.masterPackage)
            else -> {}
        }
    }

    private fun updateMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            editMasterPackageUseCase.invoke(masterPackage).fold(
                onSuccess = {
                    Log.d(
                        TAG,
                        "${MasterPackageDetailsViewModel::updateMasterPackage.name}: success"
                    )
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageDetailsViewModel::updateMasterPackage.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun updateShipment(shipment: Shipment) {
        viewModelScope.launch(Dispatchers.IO) {
            editMasterPackageShipmentUseCase.invoke(shipment).fold(
                onSuccess = {
                    Log.d(TAG, "${MasterPackageDetailsViewModel::updateShipment.name}: success")
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageDetailsViewModel::updateShipment.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun deleteShipment(shipmentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMasterPackageShipmentUseCase.invoke(shipmentId).fold(
                onSuccess = {
                    Log.d(TAG, "${MasterPackageDetailsViewModel::deleteShipment.name}: success")
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageDetailsViewModel::deleteShipment.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun addNewShipment(shipment: Shipment) {
        viewModelScope.launch(Dispatchers.IO) {
            addMasterPackageShipmentUseCase.invoke(shipment).fold(
                onSuccess = {
                    Log.d(TAG, "${MasterPackageDetailsViewModel::addNewShipment.name}: success")
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageDetailsViewModel::addNewShipment.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

}