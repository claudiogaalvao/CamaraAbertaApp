package com.claudiogalvaodev.camaraaberta.data.model.proposition

data class StatusProposicao(
    val ambito: String,
    val apreciacao: String,
    val codSituacao: Int,
    val codTipoTramitacao: String,
    val dataHora: String,
    val descricaoSituacao: String?,
    val descricaoTramitacao: String,
    val despacho: String,
    val regime: String,
    val sequencia: Int,
    val siglaOrgao: String,
    val uriOrgao: String,
    val uriUltimoRelator: String?,
    val url: String?
) {
    fun getIdUltimoRelator() = uriUltimoRelator?.split("/")?.last()?.toInt()

    fun getStatusDescription() = if (descricaoSituacao.isNullOrBlank()) {
        descricaoTramitacao
    } else descricaoSituacao

    companion object {
        fun getMock(
            despacho: String
        ) = StatusProposicao(
            ambito = "Ambito",
            apreciacao = "Apreciacao",
            codSituacao = 1,
            codTipoTramitacao = "Cod Tipo Tramitacao",
            dataHora = "2024-04-10T10:00",
            descricaoSituacao = "Descricao Situacao",
            descricaoTramitacao = "Descricao Tramitacao",
            despacho = despacho,
            regime = "Regime",
            sequencia = 1,
            siglaOrgao = "Sigla Orgao",
            uriOrgao = "Uri Orgao",
            uriUltimoRelator = "Uri Ultimo Relator",
            url = "Url"
        )
    }
}