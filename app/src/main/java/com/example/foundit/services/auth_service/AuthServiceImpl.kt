package com.example.foundit.services.auth_service

import android.content.IntentSender
import android.util.Log
import arrow.core.Either
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

    override suspend fun registerWithEmail(
        email: String,
        password: String
    ): Either<Failure, FirebaseUser?> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            Either.Right(user)
        } catch (e: Exception) {
            Log.e("TAG", "registerWithEmail: $e")
            when (e) {
                is CancellationException -> throw e
                else -> Either.Left(Failure(e.message ?: "Something went wrong"))
            }
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