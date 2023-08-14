package com.example.foundit.features.auth.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor () : ViewModel(){
    var username by mutableStateOf("")
        private  set

    fun updateUsername(username: String){
        this.username = username
    }

    var email by mutableStateOf("")
        private  set

    fun updateEmail(email: String){
        this.email = email
    }

    var password by mutableStateOf("")
        private  set

    fun updatePassword(password: String){
        this.password = password
    }

    var confirmPassword by mutableStateOf("")
        private  set

    fun updateConfirmPassword(confirmPassword: String){
        this.confirmPassword = confirmPassword
    }
}