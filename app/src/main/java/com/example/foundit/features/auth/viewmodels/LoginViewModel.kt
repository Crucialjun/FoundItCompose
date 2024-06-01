package com.example.foundit.features.auth.viewmodels

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.foundit.core.app.Resource
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.app.models.Failure
import com.example.foundit.core.constants.shared_pref.SharedPrefConsts
import com.example.foundit.features.auth.domain.params.LoginWithEmailParams
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.example.foundit.features.auth.domain.usecases.LoginWithEmailUsecase
import com.example.foundit.features.auth.domain.usecases.LoginWithGoogleUseCase
import com.example.foundit.features.auth.domain.usecases.SignInWithIntentUseCase
import com.example.foundit.features.auth.states.IntentRequestState
import com.example.foundit.features.auth.states.LoginWithEmailState
import com.example.foundit.features.auth.states.SignInWithGoogleState
import com.example.foundit.services.shared_preferences_service.SharedPreferenceService
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val signInWithIntentUseCase: SignInWithIntentUseCase,
    private val loginWithEmailUseCase: LoginWithEmailUsecase,
    private val sharedPreferencesService: SharedPreferenceService
) : ViewModel() {

    private val _signInWithGoogleState = MutableStateFlow(SignInWithGoogleState())
    val signInWithGoogleState = _signInWithGoogleState.asStateFlow()

    private val _intentRequestState = mutableStateOf(IntentRequestState())
    val intentRequestState: State<IntentRequestState> = _intentRequestState

    private val _loginWithEmailState = mutableStateOf(LoginWithEmailState())
    val loginWithEmailState: State<LoginWithEmailState> = _loginWithEmailState

    private val _appUser = mutableStateOf(AppUser())
    val appUser: State<AppUser> = _appUser

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val appUserAdapter = moshi.adapter(AppUser::class.java)

    var email by mutableStateOf("")
        private set

    fun updateEmail(email: String) {
        this.email = email
    }

    var password by mutableStateOf("")
        private set

    fun updatePassword(password: String) {
        this.password = password
    }

    var username by mutableStateOf("")
        private set

    fun updateUsername(username: String) {
        this.username = username
    }

    var isPasswordVisible by mutableStateOf(false)
        private set

    var updatePasswordVisibility: () -> Unit ={
        this.isPasswordVisible = !isPasswordVisible
    }


    suspend fun loginWithGoogle(oneTap: SignInClient) {
        Log.d("TAG", "Login with google called")
        loginWithGoogleUseCase(oneTap).onEach {
            when (it) {
                is Resource.Success -> {
                    Logger.d("Login with google Success")

                    _intentRequestState.value = IntentRequestState(intentSender = it.data)
                }

                is Resource.Error -> {
                    Logger.e("Login with google Error, error is ${it.message}")
                    _intentRequestState.value =
                        IntentRequestState(error = it.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    Logger.d("Login with google loading")
                    _intentRequestState.value = IntentRequestState(isLoading = true)
                }


            }
        }.launchIn(viewModelScope)
    }

    suspend fun signInWithIntent(intent: Intent, oneTap: SignInClient){
        signInWithIntentUseCase(
            LoginWithIntentParams(
                intent = intent,
                oneTap = oneTap
            )
        ).onEach {
            when (it) {
                is Resource.Success -> {
                Logger.d("Sign in with intent success,with${it.data?.name}}")
                   _signInWithGoogleState.value = SignInWithGoogleState(isSignInSuccess = true, appUser = it.data)
                    _appUser.value = it.data!!
                }

                is Resource.Error -> {
                    Logger.e("Sign in with intent error ${it.message}")
                }

                is Resource.Loading -> {
                    Logger.d("Login with intent Loading")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun onSignInResult(result: Either<Failure, FirebaseUser?>) {
        _signInWithGoogleState.update {
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
            is Either.Left -> SignInWithGoogleState(error = result.value.toString())
            is Either.Right -> SignInWithGoogleState(isSignInSuccess = true)
        }
    }

    suspend fun loginWithEmail(email: String, password: String) {
        loginWithEmailUseCase(
            LoginWithEmailParams(
                email = email,
                password = password
            )
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    Logger.d("Sign in with intent success,with${it.data?.name}}")

                    val appUserJson = appUserAdapter.toJson(it.data)
                    sharedPreferencesService.saveData(SharedPrefConsts.APP_USER, appUserJson)
                    _loginWithEmailState.value =
                        LoginWithEmailState(isSignInSuccess = true, appUser = it.data)

                }

                is Resource.Error -> {
                    _loginWithEmailState.value =
                        LoginWithEmailState(error = it.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _loginWithEmailState.value = LoginWithEmailState(isLoading = true)
                }


            }
        }.launchIn(viewModelScope)
    }

    fun resetState() {
        _signInWithGoogleState.update {
            SignInWithGoogleState()
        }
    }
}