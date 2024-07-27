package com.example.foundit.features.profile_setup.data.data_source.remote_data_source

import arrow.core.Either
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.services.db_service.DbService
import javax.inject.Inject

class ProfileSetupRemoteDataSourceImpl @Inject constructor(
    private val dbService: DbService
) : ProfileSetupRemoteDataSource {
    override suspend fun updateUserProfile(
        fullName: String,
        username: String,
        phoneNumber: String,
        uid: String
    ): Either<Failure, AppUser?> {
        try {
            val appUser = dbService.updateUserProfile(fullName, username, phoneNumber, uid)
            return Either.Right(appUser)
        } catch (e: Exception) {
            return Either.Left(Failure(e.message ?: "An error occurred"))
        }
    }
}
