package com.example.foundit.features.auth.presentation.views

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foundit.R
import com.example.foundit.features.auth.presentation.viewmodels.RegisterViewModel
import com.example.foundit.utils.CustomDialog
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignupScreen(

    navController: NavController
) {

    val viewmodel: RegisterViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val registerState by viewmodel.registerState.collectAsState()

    if (registerState.error != null) {
        CustomDialog(
            onDismiss = {
                viewmodel.resetState()
            },
            title = "Error",
            description = registerState.error ?: "",
            positiveAction = {

            }
        )
    }

    if (registerState.isSignInSuccess) {
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.value,
                unfocusedTextColor = OutlinedTextFieldTokens.InputColor.value,
                disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.value,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = OutlinedTextFieldTokens.CaretColor.value,
                errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.value,
                selectionColors = LocalTextSelectionColors.current,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
                errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.value,
                focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.value,
                unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.value,
                disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
                errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.value,
                focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.value,
                unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.value,
                disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
                errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.value,
                focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.value,
                unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.value,
                disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
                errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.value,
                focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.value,
                unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.value,
                disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
                errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.value,
                focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.value,
                unfocusedTextColor = OutlinedTextFieldTokens.InputColor.value,
                disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.value,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = OutlinedTextFieldTokens.CaretColor.value,
                errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.value,
                selectionColors = LocalTextSelectionColors.current,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
                errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.value,
                focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.value,
                unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.value,
                disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
                errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.value,
                focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.value,
                unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.value,
                disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
                errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.value,
                focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.value,
                unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.value,
                disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
                errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.value,
                focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.value,
                unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.value,
                disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
                errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.value,
                focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.value,
                unfocusedTextColor = OutlinedTextFieldTokens.InputColor.value,
                disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.value,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = OutlinedTextFieldTokens.CaretColor.value,
                errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.value,
                selectionColors = LocalTextSelectionColors.current,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
                errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.value,
                focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.value,
                unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.value,
                disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
                errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.value,
                focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.value,
                unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.value,
                disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
                errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.value,
                focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.value,
                unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.value,
                disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
                errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.value,
                focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.value,
                unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.value,
                disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
                errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.value,
                focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.value,
                unfocusedTextColor = OutlinedTextFieldTokens.InputColor.value,
                disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.value,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = OutlinedTextFieldTokens.CaretColor.value,
                errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.value,
                selectionColors = LocalTextSelectionColors.current,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
                errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.value,
                focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.value,
                unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.value,
                disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
                errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.value,
                focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.value,
                unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.value,
                disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
                errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.value,
                focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.value,
                unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.value,
                disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
                errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.value,
                focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.value,
                focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.value,
                unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.value,
                disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
                    .value.copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
                errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.value,
                focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.value,
                focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
                disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value
                    .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
                errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.value,
            )
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {
                Log.d("TAG", "SignupScreen:registration starting ")
                coroutineScope.launch {
                    viewmodel.register()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), shape = RoundedCornerShape(8.dp)
        ) {
            if (registerState.isLoading) {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )

            }
            Text("Register", style = TextStyle(fontWeight = FontWeight.Bold))

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
