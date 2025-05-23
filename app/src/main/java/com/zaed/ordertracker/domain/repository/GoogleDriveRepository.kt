package com.zaed.ordertracker.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface GoogleDriveRepository {
    suspend fun signOut(): Boolean
    suspend fun getSignedInAccount(): Result<GoogleSignInAccount>
}