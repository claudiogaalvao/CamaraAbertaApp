package com.example.camaraabertaapp.datasource.references.repository

import com.example.camaraabertaapp.datasource.references.model.ThemeModel

interface IReferencesRepository {

    suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>>
}