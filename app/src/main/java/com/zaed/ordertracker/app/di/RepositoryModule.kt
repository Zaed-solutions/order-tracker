package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.data.repository.AuthenticationRepositoryImpl
import com.zaed.ordertracker.data.repository.FlightRepositoryImpl
import com.zaed.ordertracker.data.repository.MpGroupRepositoryImpl
import com.zaed.ordertracker.data.repository.ShipmentRepositoryImpl
import com.zaed.ordertracker.domain.repository.AuthenticationRepository
import com.zaed.ordertracker.domain.repository.FlightRepository
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
        singleOf(::FlightRepositoryImpl) { bind<FlightRepository>() }
        singleOf(::ShipmentRepositoryImpl) { bind<ShipmentRepository>() }
        singleOf(::MpGroupRepositoryImpl) { bind<MpGroupRepository>() }
    }
