package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.domain.usecase.LoginUserUseCase
import com.zaed.ordertracker.domain.usecase.flight.CreateFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.DeleteFlightUseCase
import com.zaed.ordertracker.domain.usecase.flight.GetAllFlightsUseCase
import com.zaed.ordertracker.domain.usecase.flight.UpdateFlightUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule =
    module {
        singleOf(::LoginUserUseCase)
        singleOf(::CreateFlightUseCase)
        singleOf(::GetAllFlightsUseCase)
        singleOf(::UpdateFlightUseCase)
        singleOf(::DeleteFlightUseCase)
    }
