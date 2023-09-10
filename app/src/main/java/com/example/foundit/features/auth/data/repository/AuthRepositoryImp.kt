package com.example.foundit.features.auth.data.repository

import android.content.Intent
import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.auth.data.datasources.AuthDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor (
    private val authDataSource: AuthDataSource
)  : AuthRepository{
    override suspend fun loginWithGoogle(): IntentSender? {
        return authDataSource.loginWithGoogle()
    }

    override suspend fun signInWithIntent(intent: Intent): Either<Failure, FirebaseUser?> {
        return authDataSource.signInWithIntent(intent)
    }
}