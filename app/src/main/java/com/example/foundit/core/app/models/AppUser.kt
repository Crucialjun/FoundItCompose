package com.example.foundit.core.app.models

data class AppUser(
    val email: String = "",
    var name: String = "",
    var username: String = "",
    val profilePicUrl: String = "",
    val uid: String = "",
    val isProfile: Boolean = false,
    var phoneNumber: String = ""

)
