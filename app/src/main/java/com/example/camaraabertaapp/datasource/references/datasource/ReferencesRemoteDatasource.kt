package com.example.camaraabertaapp.datasource.references.datasource

import com.example.camaraabertaapp.datasource.references.client.ReferencesClient
import com.example.camaraabertaapp.datasource.references.dto.toModel
import com.example.camaraabertaapp.datasource.references.model.ThemeModel
import javax.inject.Inject

class ReferencesRemoteDatasource @Inject constructor(
    private val client: ReferencesClient
) : IReferencesRemoteDatasource {

    override suspend fun getAllPropositionThemes(): Result<List<ThemeModel>> {
        try {
            val response = client.getAllPropositionThemes()
            if (response.isSuccessful) {
                return Result.success(response.body()?.data?.map { it.toModel() } ?: emptyList())
            }
            return Result.failure(Exception(response.message()))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}