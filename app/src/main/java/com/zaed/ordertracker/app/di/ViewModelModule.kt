package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.ui.login.LoginViewModel
import com.zaed.ordertracker.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::SettingsViewModel)
    }
