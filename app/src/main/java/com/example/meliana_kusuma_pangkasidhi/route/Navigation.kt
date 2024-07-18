package com.example.meliana_kusuma_pangkasidhi.route

enum class Screen {
    Landing,
    List,
    ItemDetail,
    Search,
    WebView,
}

sealed class NavigationItem(val route: String) {
    object LandingScreen : NavigationItem(Screen.Landing.name)
    object ListScreen : NavigationItem(Screen.List.name)
    object DetailScreen : NavigationItem(Screen.ItemDetail.name)
    object SearchScreen: NavigationItem(Screen.Search.name)
    object WebViewScreen: NavigationItem(Screen.WebView.name)
}