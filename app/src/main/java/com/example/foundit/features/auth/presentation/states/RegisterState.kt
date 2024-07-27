package com.example.foundit.features.auth.presentation.states

import com.example.foundit.core.app.models.AppUser

data class RegisterState(
    val isSignInSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val appUser: AppUser? = null
)