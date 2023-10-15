package com.example.foundit.features.profile_setup.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            Text(text = "The user is ${viewmodel.appUser.value.name}")
            AsyncImage(
                model = "https://example.com/image.jpg",
                contentDescription = null,
            )

        }

}