package com.example.foundit.features.profile_setup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.constants.shared_pref.SharedPrefConsts
import com.example.foundit.features.profile_setup.states.ProfileSetupState
import com.example.foundit.services.shared_preferences_service.SharedPreferenceService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val sharedPreferencesService: SharedPreferenceService
) : ViewModel() {


    private val _profileSetupState = MutableStateFlow(ProfileSetupState())
    val profileSetupState = _profileSetupState.asStateFlow()


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
}
