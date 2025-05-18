package com.zaed.ordertracker.data.source.remote

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface GoogleAuth {
    suspend fun signOut(): Boolean
    suspend fun getSignedInAccount(): Result<GoogleSignInAccount>
}