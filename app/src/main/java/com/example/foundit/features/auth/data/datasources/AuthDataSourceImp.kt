package com.example.foundit.features.auth.data.datasources


import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Resources
import androidx.compose.ui.res.stringResource
import arrow.core.Either

import com.example.foundit.R
import com.example.foundit.core.app.models.Failure
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject


class AuthDataSourceImp @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient
) : AuthDataSource {

    private val auth = FirebaseAuth.getInstance()


    override suspend fun loginWithGoogle(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildGoogleSignInRequest()).await()
        } catch (e: Exception) {
            if (e is CancellationException) throw e else return null
        }

        return result.pendingIntent.intentSender
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
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
    suspend fun signInWithIntent(intent: Intent): Either<Failure, FirebaseUser?> {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            Either.Right(user)
        } catch (e: Exception) {
            Either.Left(Failure(e.toString()))
        }
    }


    private fun buildGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(
                        Resources.getSystem().getString(R.string.default_web_client_id)
                    )
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    //private val googleSignInClient = GoogleSignIn.getClient( gso)

}