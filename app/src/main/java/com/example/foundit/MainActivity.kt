package com.example.foundit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.foundit.features.auth.views.LoginScreen
import com.example.foundit.features.onboarding.OnboardingScreen

import com.example.foundit.ui.theme.FoundItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                LoginScreen()
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