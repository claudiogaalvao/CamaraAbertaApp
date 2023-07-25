package com.example.camaraabertaapp.data.references.repository

import com.example.camaraabertaapp.data.references.model.ThemeModel

interface IReferencesRepository {

    suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>>
}