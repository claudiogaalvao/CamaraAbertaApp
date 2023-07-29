package com.example.camaraabertaapp.data.references.datasource

import com.example.camaraabertaapp.data.references.client.ReferencesClient
import com.example.camaraabertaapp.data.references.dto.toModel
import com.example.camaraabertaapp.data.references.model.ThemeModel
import javax.inject.Inject

class ReferencesRemoteDatasource @Inject constructor(
    private val client: ReferencesClient
) : IReferencesRemoteDatasource {

    override suspend fun getAllPrepositionThemes(): Result<List<ThemeModel>> {
        try {
            val response = client.getAllPrepositionThemes()
            if (response.isSuccessful) {
                return Result.success(response.body()?.data?.map { it.toModel() } ?: emptyList())
            }
            return Result.failure(Exception(response.message()))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}