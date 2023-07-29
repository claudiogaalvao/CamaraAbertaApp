package com.example.camaraabertaapp.data.references.datasource

import com.example.camaraabertaapp.data.references.model.ThemeModel

interface IReferencesRemoteDatasource {

    suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>>

}