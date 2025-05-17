package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.data.repository.AuthenticationRepositoryImpl
import com.zaed.ordertracker.data.repository.FlightRepositoryImpl
import com.zaed.ordertracker.domain.repository.AuthenticationRepository
import com.zaed.ordertracker.domain.repository.FlightRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
        singleOf(::FlightRepositoryImpl) { bind<FlightRepository>() }
    }
