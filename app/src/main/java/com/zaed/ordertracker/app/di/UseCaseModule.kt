package com.zaed.ordertracker.app.di

import com.zaed.ordertracker.domain.usecase.DeleteMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.DeleteUserUseCase
import com.zaed.ordertracker.domain.usecase.GetAllUsersUseCase
import com.zaed.ordertracker.domain.usecase.GetExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.GetFirebaseCredentialUseCase
import com.zaed.ordertracker.domain.usecase.GetMpGroupsUseCase
import com.zaed.ordertracker.domain.usecase.GetSignedInAccountUseCase
import com.zaed.ordertracker.domain.usecase.LogOutUseCase
import com.zaed.ordertracker.domain.usecase.LoginUserUseCase
import com.zaed.ordertracker.domain.usecase.SaveExportFolderNameUseCase
import com.zaed.ordertracker.domain.usecase.SaveMpGroupUseCase
import com.zaed.ordertracker.domain.usecase.SaveUserUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule =
    module {
        singleOf(::LoginUserUseCase)
        singleOf(::GetSignedInAccountUseCase)
        singleOf(::DeleteMpGroupUseCase)
        singleOf(::DeleteUserUseCase)
        singleOf(::GetAllUsersUseCase)
        singleOf(::GetExportFolderNameUseCase)
        singleOf(::GetFirebaseCredentialUseCase)
        singleOf(::GetMpGroupsUseCase)
        singleOf(::LogOutUseCase)
        singleOf(::SaveExportFolderNameUseCase)
        singleOf(::SaveUserUseCase)
        singleOf(::SaveMpGroupUseCase)
    }
