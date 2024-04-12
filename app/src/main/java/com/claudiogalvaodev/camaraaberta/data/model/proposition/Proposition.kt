package com.claudiogalvaodev.camaraaberta.data.model.proposition

data class Proposition(
    val ano: Int,
    val codTipo: Int,
    val dataApresentacao: String,
    val descricaoTipo: String,
    val ementa: String,
    val ementaDetalhada: String?,
    val id: Int,
    val justificativa: String?,
    val keywords: String?,
    val numero: Int,
    val siglaTipo: String,
    val statusProposicao: StatusProposicao,
    val texto: String?,
    val uri: String,
    val uriAutores: String,
    val uriOrgaoNumerador: String?,
    val uriPropAnterior: String?,
    val uriPropPosterior: String?,
    val uriPropPrincipal: String?,
    val urlInteiroTeor: String?,
    val urnFinal: String?
) {
    fun getTitle() = "$siglaTipo $numero/$ano"
}