package com.claudiogalvaodev.camaraaberta.data.model.event

data class Orgao(
    val id: Int,
    val nome: String,
    val apelido: String,
    val codTipoOrgao: Int,
    val tipoOrgao: String,
    val nomePublicacao: String,
    val nomeResumido: String,
    val sigla: String,
    val uri: String
)