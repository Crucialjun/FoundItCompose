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
import com.example.foundit.features.auth.domain.params.LoginWithIntentParams
import com.example.foundit.features.auth.domain.usecases.LoginWithGoogleUseCase
import com.example.foundit.features.auth.domain.usecases.SignInWithIntentUseCase
import com.example.foundit.features.auth.states.IntentRequestState
import com.google.android.gms.auth.api.identity.SignInClient
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

) : ViewModel(){
    var username by mutableStateOf("")
        private  set
    var email by mutableStateOf("")
        private  set
    var password by mutableStateOf("")
        private  set
    var confirmPassword by mutableStateOf("")
        private  set











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