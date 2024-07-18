package com.example.meliana_kusuma_pangkasidhi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meliana_kusuma_pangkasidhi.route.NavigationItem
import com.example.meliana_kusuma_pangkasidhi.ui.theme.meliana_kusuma_pangkasidhiTheme
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal100
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal300
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal600
import com.example.meliana_kusuma_pangkasidhi.ui.theme.teal800

@Composable
fun LandingScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = teal600,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                color = teal300,
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                   onNavigateToListScreen(navController)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = teal800,
                )
            ) {
                Text(
                    "Click here to go next page",
                    color = teal100
                )
            }
        }

    }
}

private fun onNavigateToListScreen(navController: NavController) {
    navController.navigate(NavigationItem.ListScreen.route) {
        popUpTo(NavigationItem.LandingScreen.route) {
            inclusive = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    val navController = rememberNavController()
    meliana_kusuma_pangkasidhiTheme {
        LandingScreen(navController)
    }
}