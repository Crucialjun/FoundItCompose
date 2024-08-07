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
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.foundit.core.app.models.AppUser
import com.example.foundit.core.constants.shared_pref.SharedPrefConsts
import com.example.foundit.features.auth.presentation.viewmodels.LoginViewModel
import com.example.foundit.features.auth.presentation.views.LoginScreen
import com.example.foundit.features.auth.presentation.views.SignupScreen
import com.example.foundit.features.home.views.HomepageScreen
import com.example.foundit.features.onboarding.OnboardingScreen
import com.example.foundit.features.profile_setup.views.ProfileSetupView
import com.example.foundit.services.shared_preferences_service.SharedPreferenceServiceImpl
import com.example.foundit.ui.theme.FoundItTheme
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.identity.Identity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val LocalFacebookCallbackManager =
        staticCompositionLocalOf<CallbackManager> { error("No CallbackManager provided") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())

        val sharedPreferenceService = SharedPreferenceServiceImpl(applicationContext)

        var isFirstTime = ""

        var appUser: AppUser? = null

        lifecycleScope.launch {
            isFirstTime = sharedPreferenceService.getData("isFirstTime", "true")

            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val appUserAdapter = moshi.adapter(AppUser::class.java)


            val appUserjson = sharedPreferenceService.getData(SharedPrefConsts.APP_USER, "")

            appUser = if (appUserjson == "") null else appUserAdapter.fromJson(appUserjson)

            Logger.d("App user is $appUser")

        }







        setContent {
            val oneTapClient = Identity.getSignInClient(applicationContext)

            FoundItTheme {
                // A surface container using the 'background' color from the theme


                Surface(

                    modifier = Modifier.fillMaxSize(),

                    ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = if (isFirstTime == "true") "onboarding" else if (appUser == null) "auth" else if (appUser!!.phoneNumber.isEmpty()) "profile_setup" else "home"
                    ) {
                        navigation(
                            startDestination = "onboarding_screen",
                            route = "onboarding"
                        ) {
                            composable("onboarding_screen") {
                                OnboardingScreen(navController, sharedPreferenceService)
                            }
                        }

                        navigation(
                            startDestination = "login",
                            route = "auth"
                        ) {
                            composable("login") {
                                val viewModel: LoginViewModel = hiltViewModel()
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

                        navigation(
                            startDestination = "home_screen",
                            route = "home"
                        ) {
                            composable("home_screen") {
                                HomepageScreen()
                            }
                        }

                        navigation(
                            startDestination = "profile_setup_screen",
                            route = "profile_setup"
                        ) {
                            composable("profile_setup_screen") {
                                ProfileSetupView(navController)
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