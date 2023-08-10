package com.example.foundit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally

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
            "Welcome to FoundIt!",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 36.sp)
        )
        Spacer(Modifier.height(32.dp))
        Text("Reuniting you with your lost  items",style = TextStyle(fontWeight = FontWeight.W400, fontSize = 24.sp, textAlign = TextAlign.Center))
        Spacer(Modifier.height(64.dp))
        Button(onClick = { navController.navigate("auth") },modifier = Modifier
            .fillMaxWidth()
            .height(48.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Text("Start",style = TextStyle(fontWeight = FontWeight.Bold))

        }
    }

}

