package com.example.foundit.features.auth.domain.usecases

import android.content.IntentSender
import arrow.core.Either
import com.example.foundit.core.app.models.Failure
import com.example.foundit.core.app.NoParams
import com.example.foundit.core.app.models.Resource
import com.example.foundit.core.app.UseCases
import com.example.foundit.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCases<IntentSender?,NoParams> {
    override fun invoke(params: NoParams): Flow<Resource<IntentSender?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val intentSender = authRepository.loginWithGoogle()
                emit(Resource.Success(intentSender))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}