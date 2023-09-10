package com.example.foundit.features.auth.data.datasources

import android.content.Intent
import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.R
import com.example.foundit.core.app.models.Failure
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {
    suspend fun loginWithGoogle(): IntentSender?
    suspend fun signOut()

    suspend fun getSignedInUser(): FirebaseUser?

    suspend fun signInWithIntent(intent: Intent): Either<Failure, FirebaseUser?>





}