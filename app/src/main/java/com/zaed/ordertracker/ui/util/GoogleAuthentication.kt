package com.zaed.ordertracker.ui.util

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes

object GoogleAuthentication {
    val signInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_FILE), Scope(DriveScopes.DRIVE)).build()
}