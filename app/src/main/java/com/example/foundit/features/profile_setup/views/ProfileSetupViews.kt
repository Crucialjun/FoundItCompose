package com.example.foundit.features.profile_setup.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage


@Preview
@Composable
fun ProfileSetupView(){

        Column {
           Text("Fill in your profile")
            AsyncImage(
                model = "https://example.com/image.jpg",
                contentDescription = null,
            )

        }

}