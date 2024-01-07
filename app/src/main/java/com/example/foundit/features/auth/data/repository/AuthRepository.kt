package com.example.foundit.features.auth.data.repository

import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun loginWithGoogle(oneTap: SignInClient): IntentSender?
    suspend fun signInWithIntent(params: LoginWithIntentParams): AppUser

    suspend fun registerWithEmail(email: String, password: String): Either<Failure, FirebaseUser?>
}