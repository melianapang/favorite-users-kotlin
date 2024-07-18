package com.example.meliana_kusuma_pangkasidhi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.meliana_kusuma_pangkasidhi.route.AppNavHost
import com.example.meliana_kusuma_pangkasidhi.ui.theme.meliana_kusuma_pangkasidhiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            meliana_kusuma_pangkasidhiTheme {
                BaseScreen()
            }
        }
    }
}

@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    Surface {
        AppNavHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    meliana_kusuma_pangkasidhiTheme {
        BaseScreen()
    }
}