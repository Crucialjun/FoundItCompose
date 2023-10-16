package com.example.foundit.core.app.models

data class AppUser(
    val email: String = "",
    val name: String = "",
    val profilePicUrl: String = "",
    val uid: String = "",
    val isProfile: Boolean = false
)
