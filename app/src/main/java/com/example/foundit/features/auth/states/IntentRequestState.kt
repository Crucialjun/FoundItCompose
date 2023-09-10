package com.example.foundit.features.auth.states

import android.content.IntentSender

data class IntentRequestState(
    val intentSender: IntentSender? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
}