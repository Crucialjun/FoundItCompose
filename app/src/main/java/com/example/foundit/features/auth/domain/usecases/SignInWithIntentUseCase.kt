package com.example.foundit.features.auth.domain.usecases

import com.example.foundit.core.app.UseCases
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.Resource
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithIntentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCases<AppUser, LoginWithIntentParams>{
    override fun invoke(params: LoginWithIntentParams): Flow<Resource<AppUser>> {
        return flow{
            emit(Resource.Loading())
            try{
                val res = authRepository.signInWithIntent(params)

               emit(Resource.Success(res))
            }catch (e: Exception){
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

}