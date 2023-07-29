package com.example.camaraabertaapp.data.references.repository

import com.example.camaraabertaapp.data.references.datasource.IReferencesRemoteDatasource
import com.example.camaraabertaapp.data.references.model.ThemeModel
import javax.inject.Inject

class ReferencesRepository @Inject constructor(
    private val remoteDatasource: IReferencesRemoteDatasource
) : IReferencesRepository {

    override suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>> {
        return remoteDatasource.getAllPrepositionThemes()
    }

}