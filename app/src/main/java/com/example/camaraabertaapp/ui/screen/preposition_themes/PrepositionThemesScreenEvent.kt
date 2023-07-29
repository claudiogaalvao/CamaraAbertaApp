package com.example.camaraabertaapp.ui.screen.preposition_themes

data class PrepositionThemesScreenEvent(
    val onSelectTheme: (id: String) -> Unit,
    val onProceed: () -> Unit
)