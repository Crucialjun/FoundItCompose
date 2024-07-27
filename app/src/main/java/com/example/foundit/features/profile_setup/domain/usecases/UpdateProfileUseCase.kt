package com.example.foundit.features.profile_setup.domain.usecases

import com.example.foundit.core.app.Resource
import com.example.foundit.core.app.UseCases
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.features.profile_setup.domain.params.UpdateProfileParams
import com.example.foundit.features.profile_setup.domain.repository.ProfileSetupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileSetupRepository: ProfileSetupRepository
) : UseCases<AppUser?, UpdateProfileParams> {
    override fun invoke(params: UpdateProfileParams): Flow<Resource<AppUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val appUser = profileSetupRepository.updateUserProfile(
                    fullName = params.fullName,
                    username = params.username,
                    phoneNumber = params.phoneNumber,
                    uid = params.uid
                )
                appUser.fold(
                    ifLeft = {
                        emit(Resource.Error(it.message ?: "An error occurred"))
                    }
                ) {
                    emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }


}