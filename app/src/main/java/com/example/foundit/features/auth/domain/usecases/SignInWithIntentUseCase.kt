package com.example.foundit.features.auth.domain.usecases

import android.content.Intent
import com.example.foundit.core.app.NoParams
import com.example.foundit.core.app.UseCases
import com.example.foundit.core.app.models.Resource
import com.example.foundit.features.auth.data.repository.AuthRepository
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.example.foundit.features.auth.states.IntentRequestState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithIntentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCases<FirebaseUser?, LoginWithIntentParams>{
    override fun invoke(params: LoginWithIntentParams): Flow<Resource<FirebaseUser?>> {
        return flow{
            emit(Resource.Loading())
            try{
                val res = authRepository.signInWithIntent(params)

                res.fold(
                    ifLeft = {emit(Resource.Error(it.toString() ?: "An error occurred"))},
                    ifRight = {emit(Resource.Success(it))}
                )
            }catch (e: Exception){
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

}