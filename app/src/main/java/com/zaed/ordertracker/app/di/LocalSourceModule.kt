package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.data.source.local.LocalStorage
import com.zaed.ordertracker.data.source.local.LocalStorageImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind

val localSourceModule = module {
    singleOf(::LocalStorageImpl) {
        bind<LocalStorage>()
    }
}