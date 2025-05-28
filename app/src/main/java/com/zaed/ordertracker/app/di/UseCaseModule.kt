package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.domain.usecase.masterpackage.AddMasterPackageToGroupUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.EditMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMasterPackageWithShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMasterPackagesByFlightIdUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMpGroupWithMasterPackagesByIdUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.UpdateMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.UpdateMpGroupBackgroundColorUseCase
import com.zaed.ordertracker.domain.usecase.authentication.LoginUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.CreateFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.DeleteFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetAllFlightsUseCase
import com.zaed.ordertracker.domain.usecase.flight.UpdateFlightUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetFlightShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.DeleteMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.DeleteUserUseCase
import com.zaed.ordertracker.domain.usecase.GetAllUsersUseCase
import com.zaed.ordertracker.domain.usecase.GetExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.GetFirebaseCredentialUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.GetSignedInAccountUseCase
import com.zaed.ordertracker.domain.usecase.LogOutUseCase
import com.zaed.ordertracker.domain.usecase.SaveExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.masterpackage.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.SaveUserUseCase
import com.zaed.ordertracker.domain.usecase.UploadExcelSheetUseCase
import com.zaed.ordertracker.domain.usecase.authentication.GetCurrentUserUseCase
import com.zaed.ordertracker.domain.usecase.authentication.LogoutUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetFlightByIdUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule =
    module {
        singleOf(::LoginUserUseCase)
        singleOf(::CreateFlightUseCase)
        singleOf(::GetAllFlightsUseCase)
        singleOf(::UpdateFlightUseCase)
        singleOf(::DeleteFlightUseCase)
        singleOf(::GetFlightShipmentsUseCase)
        singleOf(::CreateShipmentUseCase)
        singleOf(::UpdateShipmentUseCase)
        singleOf(::DeleteShipmentUseCase)
        singleOf(::AddMasterPackageToGroupUseCase)
        singleOf(::AddMasterPackageUseCase)
        singleOf(::DeleteMpGroupUseCase)
        singleOf(::ExportMasterPackagesUseCase)
        singleOf(::GetMpGroupsUseCase)
        singleOf(::GetMpGroupWithMasterPackagesByIdUseCase)
        singleOf(::SaveMpGroupUseCase)
        singleOf(::UpdateMasterPackageUseCase)
        singleOf(::UpdateMpGroupBackgroundColorUseCase)
        singleOf(::GetMasterPackagesByFlightIdUseCase)
        singleOf(::EditMasterPackageUseCase)
        singleOf(::DeleteMasterPackageUseCase)
        singleOf(::GetMasterPackageWithShipmentsUseCase)
        singleOf(::GetSignedInAccountUseCase)
        singleOf(::DeleteMpGroupUseCase)
        singleOf(::DeleteUserUseCase)
        singleOf(::GetAllUsersUseCase)
        singleOf(::GetExportFolderNameUseCase)
        singleOf(::GetFirebaseCredentialUseCase)
        singleOf(::GetMpGroupsUseCase)
        singleOf(::LogOutUseCase)
        singleOf(::SaveExportFolderNameUseCase)
        singleOf(::SaveUserUseCase)
        singleOf(::SaveMpGroupUseCase)
        singleOf(::GetFlightByIdUseCase)
        singleOf(::GetCurrentUserUseCase)
        singleOf(::GetFlightByIdUseCase)
        singleOf(::UploadExcelSheetUseCase)
        singleOf(::LogoutUserUseCase)
    }
