package com.example.foundit.features.profile_setup.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foundit.features.auth.viewmodels.LoginViewModel


@Composable
fun ProfileSetupView(
    navController: NavHostController,

){

    val backStackEntry = remember{navController.getBackStackEntry("login")}
    val viewmodel: LoginViewModel = hiltViewModel(backStackEntry)
    Column {
           Text("Fill in your profile")

            AsyncImage(
                model = viewmodel.appUser.value.profilePicUrl,
                contentDescription = null,
            )

        }

}