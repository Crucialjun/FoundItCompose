package com.example.foundit.features.auth.domain.usecases

import com.example.foundit.core.app.Resource
import com.example.foundit.core.app.UseCases
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.domain.params.LoginWithEmailParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithEmailUsecase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCases<AppUser?, LoginWithEmailParams> {


    override fun invoke(params: LoginWithEmailParams): Flow<Resource<AppUser?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val appUser = authRepository.loginWithEmail(params)

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