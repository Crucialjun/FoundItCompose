package com.example.foundit.features.auth.states

data class SignInWithGoogleState(
    val isSignInSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
