package com.example.foundit.features.auth.viewmodels

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.left
import com.example.foundit.core.app.NoParams
import com.example.foundit.core.app.models.Failure
import com.example.foundit.core.app.models.Resource
import com.example.foundit.features.auth.domain.usecases.LoginWithGoogleUseCase
import com.example.foundit.features.auth.domain.usecases.SignInWithIntentUseCase
import com.example.foundit.features.auth.states.IntentRequestState
import com.example.foundit.features.auth.states.SignInState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val signInWithIntentUseCase: SignInWithIntentUseCase
) : ViewModel(){
    var username by mutableStateOf("")
        private  set
    var email by mutableStateOf("")
        private  set
    var password by mutableStateOf("")
        private  set
    var confirmPassword by mutableStateOf("")
        private  set

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _intentRequestState = mutableStateOf(IntentRequestState())
    val intentRequestState : State<IntentRequestState> = _intentRequestState



    suspend fun loginWithGoogle(){

        val intentSender = loginWithGoogleUseCase(NoParams()).onEach {
            when (it) {
                is Resource.Success -> {
                    _intentRequestState.value = IntentRequestState(intentSender = it.data)
                }

                is Resource.Error -> {
                    _intentRequestState.value =
                        IntentRequestState(error = it.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _intentRequestState.value = IntentRequestState(isLoading = true)
                }

                else -> {

                }
            }
        }
    }

    suspend fun signInWithIntent(intent: Intent){
        val res = signInWithIntentUseCase(intent).onEach {
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
        }
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

        fun resetState() {
            _state.update { SignInState() }
        }


        fun updateUsername(username: String) {
            this.username = username
        }

        fun updateEmail(email: String) {
            this.email = email
        }


        fun updatePassword(password: String) {
            this.password = password
        }


        fun updateConfirmPassword(confirmPassword: String) {
            this.confirmPassword = confirmPassword
        }
    }