package com.example.foundit.features.auth.data.datasources


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
import javax.inject.Inject


class AuthDataSourceImp @Inject constructor(
) : AuthDataSource {

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
        try {
            //oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            print(e.toString())
            if (e is CancellationException) throw e
        }
    }

    override suspend fun getSignedInUser(): FirebaseUser? {
        return auth.currentUser
    }


    override
    suspend fun signInWithIntent(params: LoginWithIntentParams): Either<Failure, FirebaseUser?> {
        val credential = params.oneTap.getSignInCredentialFromIntent(params.intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user

            Either.Right(user)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Either.Left(Failure(e.toString()))
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

    //private val googleSignInClient = GoogleSignIn.getClient( gso)

}