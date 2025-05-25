package com.zaed.ordertracker.ui.masterpkggroupdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.domain.usecase.masterpackage.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.EditMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMpGroupWithMasterPackagesByIdUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.SaveMpGroupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MasterPackageGroupDetailsViewModel(
    private val getMpGroupWithMasterPackagesByIdUseCase: GetMpGroupWithMasterPackagesByIdUseCase,
    private val addMasterPackageUseCase: AddMasterPackageUseCase,
    private val editMasterPackageUseCase: EditMasterPackageUseCase,
    private val deleteMasterPackageUseCase: DeleteMasterPackageUseCase,
    private val saveMpGroupUseCase: SaveMpGroupUseCase,
) : ViewModel() {
    private val TAG = "MasterPackageGroupDetailsViewModel"
    private val _uiState = MutableStateFlow(MasterPackageGroupDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun init(groupId: String) {
        fetchMasterPackageGroupWithMasterPackages(groupId)
    }

    private fun fetchMasterPackageGroupWithMasterPackages(groupId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMpGroupWithMasterPackagesByIdUseCase(groupId).collect { result ->
                result.fold(
                    onSuccess = { group ->
                        _uiState.value = _uiState.value.copy(
                            group = group,
                            masterPackages = group.masterPackages
                        )
                    },
                    onFailure = {
                        Log.d(
                            TAG,
                            "${MasterPackageGroupDetailsViewModel::fetchMasterPackageGroupWithMasterPackages.name}: error: ${it.message}"
                        )
                    }
                )
            }
        }
    }

    fun onAction(action: MasterPackageGroupDetailsUiAction) {
        when (action) {
            is MasterPackageGroupDetailsUiAction.OnAddNewMasterPackage -> addNewMasterPackage(action.masterPackage)
            is MasterPackageGroupDetailsUiAction.OnDeleteMasterPackage -> deleteMasterPackage(action.masterPackageId)
            is MasterPackageGroupDetailsUiAction.OnEditMasterPackage -> updateMasterPackage(action.masterPackage)
            is MasterPackageGroupDetailsUiAction.OnEditGroup -> updateGroup(action.group)
            else -> {}
        }
    }

    private fun updateGroup(group: MpGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            saveMpGroupUseCase.invoke(group).fold(
                onSuccess = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::updateGroup.name}: success"
                    )
                    fetchMasterPackageGroupWithMasterPackages(group.id)
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::updateGroup.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun updateMasterPackage(masterPackage: MasterPackage) {
        viewModelScope.launch(Dispatchers.IO) {
            editMasterPackageUseCase.invoke(masterPackage).fold(
                onSuccess = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::updateMasterPackage.name}: success"
                    )
                    fetchMasterPackageGroupWithMasterPackages(_uiState.value.group.id)
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::updateMasterPackage.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun deleteMasterPackage(masterPackageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMasterPackageUseCase.invoke(masterPackageId).fold(
                onSuccess = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::deleteMasterPackage.name}: success"
                    )
                    // Refresh the group data to show updated master package list
                    fetchMasterPackageGroupWithMasterPackages(_uiState.value.group.id)
                },
                onFailure = {
                    Log.d(
                        TAG,
                        "${MasterPackageGroupDetailsViewModel::deleteMasterPackage.name}: error: ${it.message}"
                    )
                }
            )
        }
    }

    private fun addNewMasterPackage(masterPackage: MasterPackage) {
        val updatedMasterPackages = masterPackage.apply {
            val lastIndex = uiState.value.masterPackages.size
            name = uiState.value.group.name + (lastIndex + 1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            addMasterPackageUseCase.invoke(updatedMasterPackages.copy(groupId = _uiState.value.group.id))
                .fold(
                    onSuccess = {
                        Log.d(
                            TAG,
                            "${MasterPackageGroupDetailsViewModel::addNewMasterPackage.name}: success"
                        )
                        fetchMasterPackageGroupWithMasterPackages(_uiState.value.group.id)
                    },
                    onFailure = {
                        Log.d(
                            TAG,
                            "${MasterPackageGroupDetailsViewModel::addNewMasterPackage.name}: error: ${it.message}"
                        )
                    }
                )
        }
    }
}
