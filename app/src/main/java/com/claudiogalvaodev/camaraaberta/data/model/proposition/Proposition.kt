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

    companion object {
        fun getMock(
            siglaTipo: String,
            numero: Int,
            ano: Int,
            ementa: String,
            despacho: String
        ) = Proposition(
            ano = ano,
            codTipo = 1,
            dataApresentacao = "2021-01-01",
            descricaoTipo = "Projeto de Lei",
            ementa = ementa,
            ementaDetalhada = "Ementa Detalhada",
            id = 1,
            justificativa = "Justificativa",
            keywords = "Keywords",
            numero = numero,
            siglaTipo = siglaTipo,
            statusProposicao = StatusProposicao.getMock(despacho),
            texto = "Texto",
            uri = "Uri",
            uriAutores = "Uri Autores",
            uriOrgaoNumerador = "Uri Orgao Numerador",
            uriPropAnterior = "Uri Prop Anterior",
            uriPropPosterior = "Uri Prop Posterior",
            uriPropPrincipal = "Uri Prop Principal",
            urlInteiroTeor = "Url Inteiro Teor",
            urnFinal = "Urn Final"
        )
    }
}