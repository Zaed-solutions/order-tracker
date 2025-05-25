package com.zaed.ordertracker.app.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSource
import com.zaed.ordertracker.data.source.remote.AuthenticationRemoteSourceImpl
import com.zaed.ordertracker.data.source.remote.DriveRemoteSource
import com.zaed.ordertracker.data.source.remote.DriveRemoteSourceImpl
import com.zaed.ordertracker.data.source.remote.ExportationFolderDataSource
import com.zaed.ordertracker.data.source.remote.ExportationFolderDataSourceImpl
import com.zaed.ordertracker.data.source.remote.FirebaseCredentialDataSource
import com.zaed.ordertracker.data.source.remote.FirebaseCredentialDataSourceImpl
import com.zaed.ordertracker.data.source.remote.FlightRemoteDataSource
import com.zaed.ordertracker.data.source.remote.FlightRemoteDataSourceImpl
import com.zaed.ordertracker.data.source.remote.MasterPackageRemoteSource
import com.zaed.ordertracker.data.source.remote.MasterPackageRemoteSourceImpl
import com.zaed.ordertracker.data.source.remote.MpGroupRemoteSource
import com.zaed.ordertracker.data.source.remote.MpGroupRemoteSourceImpl
import com.zaed.ordertracker.data.source.remote.ShipmentRemoteDataSource
import com.zaed.ordertracker.data.source.remote.ShipmentRemoteDataSourceImpl
import com.zaed.ordertracker.data.source.remote.UserRemoteSource
import com.zaed.ordertracker.data.source.remote.UserRemoteSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteModule =
    module {
        single<FirebaseCrashlytics> {
            Firebase.crashlytics
        }
        single<FirebaseFirestore> {
            val db = Firebase.firestore
            val settings =
                FirebaseFirestoreSettings
                    .Builder()
                    .setLocalCacheSettings(
                        PersistentCacheSettings
                            .newBuilder()
                            .setSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                            .build(),
                    ).build()
            db.firestoreSettings = settings
            db
        }
        single<FirebaseCrashlytics> {
            Firebase.crashlytics
        }
        singleOf(::AuthenticationRemoteSourceImpl) {
            bind<AuthenticationRemoteSource>()
        }
        singleOf(::FlightRemoteDataSourceImpl) {
            bind<FlightRemoteDataSource>()
        }
        singleOf(::ShipmentRemoteDataSourceImpl){
            bind<ShipmentRemoteDataSource>()
        }
        singleOf(::MpGroupRemoteSourceImpl){
            bind<MpGroupRemoteSource>()
        }
        singleOf(::MasterPackageRemoteSourceImpl){
            bind<MasterPackageRemoteSource>()
        }
        singleOf(::UserRemoteSourceImpl) {
            bind<UserRemoteSource>()
        }
        singleOf(::MpGroupRemoteSourceImpl) {
            bind<MpGroupRemoteSource>()
        }
        singleOf(::MpGroupRemoteSourceImpl) {
            bind<MpGroupRemoteSource>()
        }
        singleOf(::ExportationFolderDataSourceImpl){
            bind<ExportationFolderDataSource>()
        }
        singleOf(::FirebaseCredentialDataSourceImpl){
            bind<FirebaseCredentialDataSource>()
        }
        singleOf(::DriveRemoteSourceImpl){
            bind<DriveRemoteSource>()
        }
    }
