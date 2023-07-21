package com.example.camaraabertaapp.presentation

sealed class Screen(val route: String) {
    object PrepositionThemesScreen: Screen("preposition_themes_screen")
}