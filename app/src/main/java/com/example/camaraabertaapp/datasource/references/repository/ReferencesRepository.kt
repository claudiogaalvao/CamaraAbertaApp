package com.example.camaraabertaapp.datasource.references.repository

import com.example.camaraabertaapp.datasource.references.datasource.IReferencesRemoteDatasource
import com.example.camaraabertaapp.datasource.references.model.ThemeModel
import javax.inject.Inject

class ReferencesRepository @Inject constructor(
    private val remoteDatasource: IReferencesRemoteDatasource
) : IReferencesRepository {

    override suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>> {
        return remoteDatasource.getAllPropositionThemes()
    }

}