package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.domain.usecase.LoginUserUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule =
    module {
        singleOf(::LoginUserUseCase)
    }
