package com.example.camaraabertaapp.presentation

sealed class Screen(val route: String) {
    object PrepositionThemesScreen: Screen("preposition_themes_screen")
    object EventsScreen: Screen("events_screen")
}