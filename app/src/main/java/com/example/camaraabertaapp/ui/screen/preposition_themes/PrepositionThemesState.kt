package com.example.camaraabertaapp.ui.screen.preposition_themes

import com.example.camaraabertaapp.data.references.model.ThemeModel

data class PrepositionThemesState(
    val isLoading: Boolean = false,
    val themes: List<ThemeModel> = emptyList(),
    val error: String = "",
    val isButtonEnabled: Boolean = false
)