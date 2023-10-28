package com.example.foundit.features.profile_setup.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foundit.features.auth.viewmodels.LoginViewModel
import com.example.foundit.features.profile_setup.viewmodels.ProfileSetupViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupView(
    navController: NavHostController,

    ) {

    val backStackEntry = remember { navController.getBackStackEntry("login") }
    val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
    val viewmodel: ProfileSetupViewModel = hiltViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Fill in your profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(

            model = loginViewModel.appUser.value.profilePicUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(140.dp)
                .height(140.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = loginViewModel.username,
            onValueChange = {
                loginViewModel.updateUsername(it)

            })

    }

}