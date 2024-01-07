package com.example.foundit.features.auth.viewmodels


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foundit.core.app.Resource
import com.example.foundit.features.auth.domain.params.RegisterWithEmailParams
import com.example.foundit.features.auth.domain.usecases.RegisterWithEmailUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerWithEmailUsecase: RegisterWithEmailUsecase
) : ViewModel() {
    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

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

    suspend fun register() {
        Log.d("TAG", "register: registration at viewmodel stage")
        registerWithEmailUsecase(
            RegisterWithEmailParams(
                email = email,
                password = password,

                )
        ).onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("TAG", "register: loading")
                }

                is Resource.Success -> {
                    Log.d("TAG", "register: success")
                }

                is Resource.Error -> {
                    Log.d("TAG", "register: error")
                }


            }
        }.launchIn(viewModelScope)

    }
}