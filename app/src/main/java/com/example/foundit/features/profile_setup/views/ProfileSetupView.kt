package com.example.foundit.features.profile_setup.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.foundit.features.profile_setup.viewmodels.ProfileSetupViewModel
import com.togitech.ccp.component.TogiCountryCodePicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupView(
    navController: NavHostController,

    ) {

    //val backStackEntry = remember { navController.getBackStackEntry("auth") }
    //val loginViewModel: LoginViewModel = hiltViewModel(backStackEntry)
    val viewmodel: ProfileSetupViewModel = hiltViewModel()


    val profileSetupState by viewmodel.profileSetupState.collectAsState()

    if (profileSetupState.isLoading) {
        CircularProgressIndicator()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Fill in your profile", fontSize = 32.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(20.dp))
//        AsyncImage(
//
//            model = loginViewModel.appUser.value.profilePicUrl,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .width(140.dp)
//                .height(140.dp)
//                .clip(CircleShape)
//                .border(2.dp, Color.Gray, CircleShape)
//        )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                placeholder = { Text("Full Name") },
                label = { Text("Full Name") },
                value = viewmodel.fullName,
                onValueChange = {
                    viewmodel.updateFullName(it)

                },

                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),

                )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                placeholder = { Text("Username") },
                label = { Text("Username") },
                value = profileSetupState.appUser?.name ?: "",
                onValueChange = {


                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),

                )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                placeholder = { Text("email") },
                label = { Text("Email") },
                value = profileSetupState.appUser?.email ?: "",
                onValueChange = {


                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),

                )
            Spacer(modifier = Modifier.height(16.dp))
            TogiCountryCodePicker(
                onValueChange = {

                },
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp),
                text = "Phone Number",

                )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Continue", style = TextStyle(fontWeight = FontWeight.Bold))

            }


        }
    }
    //show loading


}