package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.app.data.repository.AuthenticationRepository
import com.zaed.ordertracker.app.data.repository.AuthenticationRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
    }
