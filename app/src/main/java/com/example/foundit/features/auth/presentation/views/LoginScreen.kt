package com.example.foundit.features.auth.presentation.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foundit.R
import com.example.foundit.features.auth.viewmodels.LoginViewModel
import com.example.foundit.utils.CustomDialog
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginScreen(
    navController: NavController,
    onSignInWithGoogleClick : () -> Unit,
    ) {
    val context = LocalContext.current


    val viewmodel: LoginViewModel = hiltViewModel()
    val intentRequestState by viewmodel.intentRequestState
    val signInWithGoogleState by viewmodel.signInWithGoogleState.collectAsState()
    val loginWithEmailState by viewmodel.loginWithEmailState
    val coroutineScope = rememberCoroutineScope()

    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()




    LaunchedEffect(key1 = signInWithGoogleState) {
        if (
            signInWithGoogleState.isSignInSuccess
        ) {
            Toast.makeText(context, "Sign in Success", Toast.LENGTH_LONG).show()
            viewmodel.updateUsername(signInWithGoogleState.appUser?.name ?: "")
            navController.navigate("profile_setup")
        }

    }

    if (loginWithEmailState.error != null) {
        CustomDialog(
            onDismiss = {
                viewmodel.resetState()
            },
            title = "Error",
            description = loginWithEmailState.error ?: "",
            positiveAction = {

            }
        )
    }

    if (loginWithEmailState.isSignInSuccess) {
        navController.navigate("profile_setup") {
            popUpTo("auth") {
                inclusive = true
            }
        }
    }






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
            "Log in to FoundIt!",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp)
        )
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                   onSignInWithGoogleClick()


                }, modifier = Modifier
                    .height(48.dp)
                    .weight(1f), shape = RoundedCornerShape(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Continue with ", style = TextStyle(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                    )

                }

            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {

                }, modifier = Modifier

                    .height(48.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook Logo",
                    )
                }

            }

        }
        Spacer(Modifier.height(32.dp))
        Text(
            "Or use email",
            style = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
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
        OutlinedTextField(
            value = viewmodel.password,
            onValueChange = {
                viewmodel.updatePassword(it)
            },
            singleLine = true,
            visualTransformation = if (viewmodel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,


                ),
            trailingIcon = {
                Icon(
                    painter = if (viewmodel.isPasswordVisible) painterResource(id = R.drawable.visibility_24) else painterResource(id = R.drawable.baseline_visibility_off_24),
                    contentDescription = "Eye",
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                        .clickable {
                            viewmodel.updatePasswordVisibility()
                        }
                )
            }
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    viewmodel.loginWithEmail(
                        viewmodel.email,
                        viewmodel.password
                    )
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), shape = RoundedCornerShape(8.dp)
        ) {
            if (loginWithEmailState.isLoading) {
                CircularProgressIndicator()
            } else
                Text("Sign In", style = TextStyle(fontWeight = FontWeight.Bold))

        }
        Spacer(Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                "Don't have an account? ",
                style = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp)
            )
            ClickableText(
                text = AnnotatedString(" Register Now"),
                onClick = {
                    navController.navigate("register")
                },
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
            )

        }
    }
}



