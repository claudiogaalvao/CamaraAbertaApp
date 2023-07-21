package com.example.camaraabertaapp.datasource.references.datasource

import com.example.camaraabertaapp.datasource.references.model.ThemeModel

interface IReferencesRemoteDatasource {

    suspend fun getAllPropositionThemes(): Result<List<ThemeModel>>

}