package com.zaed.ordertracker.app.di

import org.koin.dsl.module

val appModule =
    module {
        includes(viewmodelModule, useCaseModule, repositoryModule, remoteModule, localSourceModule)
    }
