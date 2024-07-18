package com.example.meliana_kusuma_pangkasidhi.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.meliana_kusuma_pangkasidhi.ui.screens.DetailScreen
import com.example.meliana_kusuma_pangkasidhi.ui.screens.LandingScreen
import com.example.meliana_kusuma_pangkasidhi.ui.screens.ListScreen
import com.example.meliana_kusuma_pangkasidhi.ui.screens.SearchScreen
import com.example.meliana_kusuma_pangkasidhi.ui.screens.WebViewScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.LandingScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.LandingScreen.route) {
            LandingScreen(navController)
        }

        composable(NavigationItem.ListScreen.route) {
            ListScreen(navController)
        }

        composable("${NavigationItem.DetailScreen.route}/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) {
            DetailScreen(navController)
        }

        composable(NavigationItem.SearchScreen.route) {
            SearchScreen(navController)
        }

        composable("${NavigationItem.WebViewScreen.route}/{url}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )
        ) {
            WebViewScreen(navController)
        }
    }
}