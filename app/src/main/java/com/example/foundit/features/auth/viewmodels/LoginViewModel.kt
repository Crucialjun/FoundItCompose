package com.example.foundit.features.auth.viewmodels

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.foundit.core.app.models.Failure
import com.example.foundit.core.app.models.Resource
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.example.foundit.features.auth.domain.usecases.LoginWithGoogleUseCase
import com.example.foundit.features.auth.domain.usecases.SignInWithIntentUseCase
import com.example.foundit.features.auth.states.IntentRequestState
import com.example.foundit.features.auth.states.SignInState
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val signInWithIntentUseCase: SignInWithIntentUseCase
) : ViewModel(){

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _intentRequestState = mutableStateOf(IntentRequestState())
    val intentRequestState : State<IntentRequestState> = _intentRequestState


    suspend fun loginWithGoogle(oneTap :SignInClient){
        Log.d("TAG", "Login with google called")
        val intentSender = loginWithGoogleUseCase(oneTap).onEach {
            when (it) {
                is Resource.Success -> {
                    Log.e("TAG", "loginWithGoogle:Success... result is ${it.data}", )
                    _intentRequestState.value = IntentRequestState(intentSender = it.data)
                }

                is Resource.Error -> {
                    Log.e("TAG", "loginWithGoogle:Error is ${it.message} ", )
                    _intentRequestState.value =
                        IntentRequestState(error = it.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    Log.e("TAG", "loginWithGoogle:Loading... ", )
                    _intentRequestState.value = IntentRequestState(isLoading = true)
                }

                else -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun signInWithIntent(intent: Intent, oneTap: SignInClient){
        val res = signInWithIntentUseCase(
            LoginWithIntentParams(
                intentSender = intent,
                oneTap = oneTap
            )
        ).onEach {
            when (it) {
                is Resource.Success -> {

                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                else -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSignInResult(result: Either<Failure, FirebaseUser?>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = result.fold(
                    ifLeft = { failure -> failure.toString() },
                    ifRight = { null }
                ),
                isSignInSuccess = result.fold(
                    ifLeft = { false },
                    ifRight = { true }
                )
            )

        }

        when (result) {
            is Either.Left -> SignInState(error = result.value.toString())
            is Either.Right -> SignInState(isSignInSuccess = true)
        }
    }
}