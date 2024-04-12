package com.claudiogalvaodev.camaraaberta.data.repository

import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.model.deputados.Deputado

class DeputadosRepository(
    private val client: Client
) {
    suspend fun getDeputado(
        id: Int
    ): Result<Deputado> {
        return try {
            val result = client.getDeputado(id)
            Result.success(result.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}