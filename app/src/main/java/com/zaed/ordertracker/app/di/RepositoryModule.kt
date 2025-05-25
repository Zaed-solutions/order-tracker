package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.data.repository.AuthenticationRepositoryImpl
import com.zaed.ordertracker.data.repository.FileExportRepositoryImpl
import com.zaed.ordertracker.data.repository.FirebaseCredentialRepositoryImpl
import com.zaed.ordertracker.data.repository.FlightRepositoryImpl
import com.zaed.ordertracker.data.repository.GoogleDriveRepositoryImpl
import com.zaed.ordertracker.data.repository.MpGroupRepositoryImpl
import com.zaed.ordertracker.data.repository.ShipmentRepositoryImpl
import com.zaed.ordertracker.data.repository.UserRepositoryImpl
import com.zaed.ordertracker.data.source.remote.GoogleAuth
import com.zaed.ordertracker.data.source.remote.GoogleAuthImpl
import com.zaed.ordertracker.domain.repository.AuthenticationRepository
import com.zaed.ordertracker.domain.repository.FileExportRepository
import com.zaed.ordertracker.domain.repository.FirebaseCredentialRepository
import com.zaed.ordertracker.domain.repository.FlightRepository
import com.zaed.ordertracker.domain.repository.GoogleDriveRepository
import com.zaed.ordertracker.domain.repository.MpGroupRepository
import com.zaed.ordertracker.domain.repository.ShipmentRepository
import com.zaed.ordertracker.domain.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
        singleOf(::FlightRepositoryImpl) { bind<FlightRepository>() }
        singleOf(::ShipmentRepositoryImpl) { bind<ShipmentRepository>() }
        singleOf(::MpGroupRepositoryImpl) { bind<MpGroupRepository>() }
        singleOf(::FileExportRepositoryImpl) { bind<FileExportRepository>() }
        singleOf(::MpGroupRepositoryImpl) { bind<MpGroupRepository>() }
        singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
        singleOf(::FirebaseCredentialRepositoryImpl) { bind<FirebaseCredentialRepository>() }
        singleOf(::GoogleDriveRepositoryImpl) { bind<GoogleDriveRepository>() }
        singleOf(::GoogleAuthImpl) { bind<GoogleAuth>() }
    }
