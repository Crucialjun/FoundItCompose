package com.example.foundit.services.auth_service

import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class AuthServiceImpl : AuthService {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun loginWithGoogle(oneTap: SignInClient): IntentSender {
        val result = try {
            oneTap.beginSignIn(buildGoogleSignInRequest()).await()
        } catch (e: Exception) {
            throw e

        }

        return result.pendingIntent.intentSender
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun getSignedInUser(): FirebaseUser? {
        try {
            return auth.currentUser
        }catch (e: Exception){
            print(e.toString())
            throw e
        }

    }

    override suspend fun signInWithIntent(params: LoginWithIntentParams) : FirebaseUser? {
        val credential = params.oneTap.getSignInCredentialFromIntent(params.intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        try {
            return auth.signInWithCredential(googleCredentials).await().user

        } catch (e: Exception) {
             throw e

        }
    }

    private fun buildGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(
                        "733567715373-6c6uoa8nutgghj1o8547mh3jt17ufv68.apps.googleusercontent.com"
                    )
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

}