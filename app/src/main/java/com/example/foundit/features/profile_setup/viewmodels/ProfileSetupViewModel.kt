package com.example.foundit.features.profile_setup.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foundit.core.app.Resource
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.constants.shared_pref.SharedPrefConsts
import com.example.foundit.features.profile_setup.domain.params.UpdateProfileParams
import com.example.foundit.features.profile_setup.domain.usecases.UpdateProfileUseCase
import com.example.foundit.features.profile_setup.states.ProfileSetupState
import com.example.foundit.services.shared_preferences_service.SharedPreferenceService
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val sharedPreferencesService: SharedPreferenceService,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val appUserAdapter = moshi.adapter(AppUser::class.java)


    private val _profileSetupState = MutableStateFlow(ProfileSetupState())
    val profileSetupState = _profileSetupState.asStateFlow()

    private var _fullName by mutableStateOf("")

    val fullName: String
        get() = _fullName

    fun updateFullName(fullName: String) {
        this._fullName = fullName
    }

    private var userName by mutableStateOf("")

    fun updateUserName(userName: String) {
        this.userName = userName
    }

    private var phoneNumber by mutableStateOf("")

    fun updatePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    private var email by mutableStateOf("")

    fun updateEmail(email: String) {
        this.email = email
    }


    init {
        viewModelScope.launch {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val appUserAdapter = moshi.adapter(AppUser::class.java)
            val appUserjson = sharedPreferencesService.getData(SharedPrefConsts.APP_USER, "")
            val appUser = if (appUserjson == "") null else appUserAdapter.fromJson(appUserjson)

            if (appUser != null) {
                _profileSetupState.value = ProfileSetupState(appUser = appUser)
            }
        }


    }

    suspend fun updateUserProfile(
        uid: String,
    ) {
        Logger.d("updateUserProfile called")
        updateProfileUseCase(
            UpdateProfileParams(
                fullName = fullName,
                username = userName,
                phoneNumber = phoneNumber,
                uid = uid
            )
        ).onEach {
            when (it) {
                is Resource.Loading -> {
                    Logger.d("Loading")
                    _profileSetupState.value = ProfileSetupState(isLoading = true)
                }

                is Resource.Success -> {
                    val appUserJson = appUserAdapter.toJson(it.data)

                    sharedPreferencesService.saveData(SharedPrefConsts.APP_USER, appUserJson)
                    _profileSetupState.value =
                        ProfileSetupState(appUser = it.data, isProfileSetupSuccess = true)
                }

                is Resource.Error -> {
                    Logger.e("Error, error is ${it.message}")
                    _profileSetupState.value = ProfileSetupState(error = it.message)
                }
            }


        }.launchIn(viewModelScope)


    }
}
