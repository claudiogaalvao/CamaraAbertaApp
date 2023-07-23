package com.example.camaraabertaapp.presentation.preposition_themes

import com.example.camaraabertaapp.datasource.references.model.ThemeModel

data class PrepositionThemesState(
    val isLoading: Boolean = false,
    val themes: List<ThemeModel> = emptyList(),
    val error: String = "",
    val isButtonEnabled: Boolean = false
)