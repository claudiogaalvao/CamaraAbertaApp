package com.example.camaraabertaapp.ui.screen

sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash_screen")
    object PrepositionThemesScreen: Screen("preposition_themes_screen")
    object EventsScreen: Screen("events_screen")

}