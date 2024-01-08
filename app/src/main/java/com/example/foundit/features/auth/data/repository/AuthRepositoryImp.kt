package com.example.foundit.features.auth.data.repository

import android.content.IntentSender
import android.util.Log
import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.auth.data.datasources.AuthDataSource
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.google.android.gms.auth.api.identity.SignInClient
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor (
    private val authDataSource: AuthDataSource
)  : AuthRepository {
    override suspend fun loginWithGoogle(oneTap: SignInClient): IntentSender? {
        return authDataSource.loginWithGoogle(oneTap)
    }

    override suspend fun signInWithIntent(params: LoginWithIntentParams): AppUser {
        return authDataSource.signInWithIntent(params)
    }

    override suspend fun registerWithEmail(
        email: String,
        password: String,
        username: String
    ): Either<Failure, AppUser?> {
        Log.d("TAG", "registerWithEmail: registraton at repository ")
        return authDataSource.registerWithEmail(email, password, username)
    }
}