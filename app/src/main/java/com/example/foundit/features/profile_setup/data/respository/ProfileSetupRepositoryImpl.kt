package com.example.foundit.features.profile_setup.data.respository

import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.features.profile_setup.data.data_source.remote_data_source.ProfileSetupRemoteDataSource
import com.example.foundit.features.profile_setup.domain.repository.ProfileSetupRepository
import javax.inject.Inject

class ProfileSetupRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileSetupRemoteDataSource
) : ProfileSetupRepository {
    override suspend fun updateUserProfile(
        fullName: String,
        username: String,
        phoneNumber: String,
        uid: String
    ): Either<Failure, AppUser?> {
        return remoteDataSource.updateUserProfile(fullName, username, phoneNumber, uid)
    }
}