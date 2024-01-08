package com.example.foundit.features.auth.domain.params

data class RegisterWithEmailParams(
    val email: String,
    val password: String,
    val username: String
)