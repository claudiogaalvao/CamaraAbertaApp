package com.claudiogalvaodev.camaraaberta.data.model.deputados

data class UltimoStatus(
    val condicaoEleitoral: String,
    val data: String,
    val descricaoStatus: String?,
    val email: String,
    val gabinete: Gabinete,
    val id: Int,
    val idLegislatura: Int,
    val nome: String,
    val nomeEleitoral: String,
    val siglaPartido: String,
    val siglaUf: String,
    val situacao: String,
    val uri: String,
    val uriPartido: String,
    val urlFoto: String
)