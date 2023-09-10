package com.example.foundit.features.auth.data.repository

import android.content.Intent
import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.Failure
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun loginWithGoogle(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): Either<Failure, FirebaseUser?>
}