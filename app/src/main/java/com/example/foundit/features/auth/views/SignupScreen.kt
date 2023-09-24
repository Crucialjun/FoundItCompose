package com.example.foundit.features.auth.views

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import arrow.core.Either
import com.example.foundit.R
import com.example.foundit.features.auth.viewmodels.LoginViewModel
import com.example.foundit.features.auth.viewmodels.RegisterViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignupScreen(

    navController: NavController
)  {
    var email by remember { mutableStateOf("") }
    val viewmodel : RegisterViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.Start

    ) {
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()

        )
        Spacer(Modifier.height(64.dp))
        Text(
            "Register to FoundIt!",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp)
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = viewmodel.username,
            onValueChange = {
                viewmodel.updateUsername(it)
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier =  Modifier.height(8.dp))

        OutlinedTextField(
            value = viewmodel.email,
            onValueChange = {
                viewmodel.updateEmail(it)
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier =  Modifier.height(8.dp))
        OutlinedTextField(
            value = viewmodel.password,
            onValueChange = {
                viewmodel.updatePassword(it)
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,


                )
        )
        Spacer(modifier =  Modifier.height(8.dp))
        OutlinedTextField(
            value = viewmodel.confirmPassword,
            onValueChange = {
                viewmodel.updateConfirmPassword(it)
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,


                )
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {

            }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Text("Sign In2", style = TextStyle(fontWeight = FontWeight.Bold))

        }
        Spacer(Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                "Already have an account? ",
                style = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp)
            )
            Text(
                " Sign in",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                textAlign = TextAlign.Center,

                )
        }
    }
}
