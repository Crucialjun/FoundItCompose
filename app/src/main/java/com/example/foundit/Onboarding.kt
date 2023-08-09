package com.example.foundit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun OnboardingScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp), horizontalAlignment = Alignment.CenterHorizontally

    ){
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search",
        )
        Text("Welcome to FoundIt!", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Text("FoundIt - Reuniting you with your lost  items")
    }

}

