package com.zaed.ordertracker.data.source.remote

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import java.io.File

interface DriveRemoteSource {
    fun uploadExcelToFolder(
        account: GoogleSignInAccount,
        file: File,
        folderName: String
    ): Flow<Result<String>>
}
