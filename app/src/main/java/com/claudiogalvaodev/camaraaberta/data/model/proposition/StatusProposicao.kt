package com.claudiogalvaodev.camaraaberta.data.model.proposition

data class StatusProposicao(
    val ambito: String,
    val apreciacao: String,
    val codSituacao: Int,
    val codTipoTramitacao: String,
    val dataHora: String,
    val descricaoSituacao: String,
    val descricaoTramitacao: String,
    val despacho: String,
    val regime: String,
    val sequencia: Int,
    val siglaOrgao: String,
    val uriOrgao: String,
    val uriUltimoRelator: String?,
    val url: String?
)