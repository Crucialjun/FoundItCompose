package com.example.foundit.features.profile_setup.states

import com.example.foundit.core.app.models.AppUser

data class ProfileSetupState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val appUser: AppUser? = null
)
