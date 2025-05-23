package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.ui.flights.FlightsViewModel
import com.zaed.ordertracker.ui.home.FlightDetailsViewModel
import com.zaed.ordertracker.ui.login.LoginViewModel
import com.zaed.ordertracker.ui.masterpackagedetails.MasterPackageDetailsViewModel
import com.zaed.ordertracker.ui.masterpkggroupdetails.MasterPackageGroupDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::FlightsViewModel)
        viewModelOf(::FlightDetailsViewModel)
        viewModelOf(::MasterPackageGroupDetailsViewModel)
        viewModelOf(::MasterPackageDetailsViewModel)
    }
