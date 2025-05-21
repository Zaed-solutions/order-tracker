package com.zaed.ordertracker.app.di

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
    }
