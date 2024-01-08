package com.example.foundit.features.auth.domain.usecases

import android.util.Log
import com.example.foundit.core.app.Resource
import com.example.foundit.core.app.UseCases
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.domain.params.RegisterWithEmailParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RegisterWithEmailUsecase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCases<AppUser?, RegisterWithEmailParams> {
    override fun invoke(params: RegisterWithEmailParams): Flow<Resource<AppUser?>> {
        Log.d("TAG", "invoke: register at usecase")
        return flow {
            emit(Resource.Loading())
            try {
                val firebaseUser =
                    authRepository.registerWithEmail(params.email, params.password, params.username)
                Log.d("TAG", "invoke: received user is $firebaseUser")
                firebaseUser.fold(
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
