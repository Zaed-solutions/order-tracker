package com.zaed.ordertracker.data.source.remote
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.zaed.ordertracker.ui.util.GoogleAuthentication.signInOptions


class GoogleAuthImpl(
    private val context: Context
) : GoogleAuth {
    override suspend fun signOut(): Boolean {
        try {
            GoogleSignIn.getClient(context, signInOptions).signOut()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override suspend fun getSignedInAccount(): Result<GoogleSignInAccount>  {
        try {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            return if (account != null) {
                (Result.success(account))
            } else {
                (Result.failure(Exception("No account found")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return (Result.failure(e))
        }
    }
}