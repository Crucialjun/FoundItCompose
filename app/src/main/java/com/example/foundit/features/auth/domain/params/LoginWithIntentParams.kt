package com.example.foundit.features.auth.domain.params

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.SignInClient

data class LoginWithIntentParams(
    val oneTap : SignInClient,
    val intentSender: Intent
) {
}