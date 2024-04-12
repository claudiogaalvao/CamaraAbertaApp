package com.claudiogalvaodev.camaraaberta.data.repository

import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.model.proposition.Proposition

class PropositionsRepository(
    private val client: Client
){
    suspend fun getProposition(
        id: Int
    ): Result<Proposition> {
        return try {
            val result = client.getProposition(id)
            Result.success(result.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}