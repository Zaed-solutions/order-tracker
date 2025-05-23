package com.zaed.ordertracker.ui.masterpkggroupdetails

import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup

sealed interface MasterPackageGroupDetailsUiAction {
    data class OnAddNewMasterPackage(val masterPackage: MasterPackage) : MasterPackageGroupDetailsUiAction
    data class OnEditMasterPackage(val masterPackage: MasterPackage) : MasterPackageGroupDetailsUiAction
    data class OnDeleteMasterPackage(val masterPackageId: String) : MasterPackageGroupDetailsUiAction
    data class OnEditGroup(val group: MpGroup) : MasterPackageGroupDetailsUiAction
    data class OnMasterPackageClicked(val masterPackage: MasterPackage) : MasterPackageGroupDetailsUiAction
    data object OnBack : MasterPackageGroupDetailsUiAction
}