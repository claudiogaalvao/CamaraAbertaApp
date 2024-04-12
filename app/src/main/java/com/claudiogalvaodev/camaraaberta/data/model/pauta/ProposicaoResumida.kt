package com.claudiogalvaodev.camaraaberta.data.model.pauta

import kotlin.random.Random

data class ProposicaoResumida(
    val ano: Int,
    val codTipo: Int,
    val ementa: String,
    val id: Int,
    val numero: Int,
    val siglaTipo: String,
    val uri: String
) {
    companion object {
        fun getMock(
            ementa: String
        ) = ProposicaoResumida(
            ano = 1,
            codTipo = 1,
            ementa = ementa,
            id = Random.nextInt(),
            numero = 1,
            siglaTipo = "siglaTipo",
            uri = "uri"
        )
    }
}