package com.example.foundit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.foundit.features.auth.viewmodels.LoginViewModel
import com.example.foundit.features.auth.views.LoginScreen
import com.example.foundit.features.auth.views.SignupScreen
import com.example.foundit.features.onboarding.OnboardingScreen
import com.example.foundit.ui.theme.FoundItTheme
import com.google.android.gms.auth.api.identity.Identity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())

        setContent {
            val oneTapClient = Identity.getSignInClient(applicationContext)
            FoundItTheme {
                // A surface container using the 'background' color from the theme


                Surface(

                    modifier = Modifier.fillMaxSize(),

                    ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "onboarding") {
                        navigation(
                            startDestination = "onboarding_screen",
                            route = "onboarding"
                        ) {
                            composable("onboarding_screen") {
                                OnboardingScreen(navController)
                            }
                        }

                        navigation(
                            startDestination = "login",
                            route = "auth"
                        ) {
                            composable("login") {
                                val viewModel :LoginViewModel = hiltViewModel()
                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        Logger.d("Result is $result and code is ${result.resultCode}")
                                        if(result.resultCode == RESULT_OK){
                                            lifecycleScope.launch {
                                                viewModel.signInWithIntent(result.data ?: return@launch, oneTapClient)
                                            }
                                        }
                                    }
                                )
                                LoginScreen(navController) {
                                    lifecycleScope.launch {
                                        viewModel.loginWithGoogle(oneTapClient).also {
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    viewModel.intentRequestState.value.intentSender
                                                        ?: return@launch

                                                ).build()
                                            )
                                        }

                                    }
                                }
                            }
                            composable("register") {
                                SignupScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoundItTheme {
        Greeting("Android")
    }
}