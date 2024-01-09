package com.example.foundit.services.navigation_service

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NavigationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NavigationService {
    private val navController = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }


    override fun navigate(route: String) {
        navController.navigate(route)
    }
}