package com.zaed.ordertracker.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.zaed.ordertracker.data.source.remote.GoogleAuth
import com.zaed.ordertracker.domain.repository.GoogleDriveRepository

class GoogleDriveRepositoryImpl(
    private val googleAuth: GoogleAuth
) : GoogleDriveRepository {
    override suspend fun signOut() = googleAuth.signOut()

    override suspend fun getSignedInAccount() = googleAuth.getSignedInAccount()
}