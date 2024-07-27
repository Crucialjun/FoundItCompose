package com.example.foundit.features.profile_setup.data.data_source.remote_data_source

import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure

interface ProfileSetupRemoteDataSource {
    suspend fun updateUserProfile(

        fullName: String,
        username: String,
        phoneNumber: String,
        uid: String
    ): Either<Failure, AppUser?>

}