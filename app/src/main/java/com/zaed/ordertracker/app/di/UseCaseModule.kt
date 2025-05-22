package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.domain.usecase.AddMasterPackageToGroupUseCase
import com.zaed.ordertracker.domain.usecase.AddMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.DeleteMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.DeleteMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.EditMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.ExportMasterPackagesUseCase
import com.zaed.ordertracker.domain.usecase.GetMasterPackageWithShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.GetMasterPackagesByFlightIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupByIdUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMasterPackageUseCase
import com.zaed.ordertracker.domain.usecase.UpdateMpGroupBackgroundColorUseCase
import com.zaed.ordertracker.domain.usecase.authentication.LoginUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.CreateFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.DeleteFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetAllFlightsUseCase
import com.zaed.ordertracker.domain.usecase.flight.UpdateFlightUseCase
import com.zaed.ordertracker.domain.usecase.shipment.CreateShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.DeleteShipmentUseCase
import com.zaed.ordertracker.domain.usecase.shipment.GetFlightShipmentsUseCase
import com.zaed.ordertracker.domain.usecase.shipment.UpdateShipmentUseCase
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
        singleOf(::GetMpGroupByIdUseCase)
        singleOf(::SaveMpGroupUseCase)
        singleOf(::UpdateMasterPackageUseCase)
        singleOf(::UpdateMpGroupBackgroundColorUseCase)
        singleOf(::GetMasterPackagesByFlightIdUseCase)
        singleOf(::EditMasterPackageUseCase)
        singleOf(::DeleteMasterPackageUseCase)
        singleOf(::GetMasterPackageWithShipmentsUseCase)
    }
