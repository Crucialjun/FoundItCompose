package com.example.foundit.features.profile_setup.domain.repository

import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure

interface ProfileSetupRepository {
    suspend fun updateUserProfile(
        fullName: String,
        username: String,
        phoneNumber: String,
        uid: String
    ): Either<Failure, AppUser?>
}