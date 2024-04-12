package com.claudiogalvaodev.camaraaberta.data.repository

import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.model.deputados.Deputado
import com.claudiogalvaodev.camaraaberta.data.model.proposition.Author
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

    suspend fun getPropositionAuthors(
        propositionId: Int
    ): Result<List<Deputado>> {
        return try {
            val result = client.getPropositionAuthors(propositionId)
            val authorsId = result.data.map { it.getDeputadoId() }
            val deputados: MutableList<Deputado> = mutableListOf()
            authorsId.forEach { id ->
                val deputado = client.getDeputado(id).data
                deputados.add(deputado)
            }
            Result.success(deputados)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUltimoRelator(
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