package com.claudiogalvaodev.camaraaberta.data.model.pauta

import com.google.gson.annotations.SerializedName

data class Pauta(
    val codRegime: Int,
    val ordem: Int,
    @SerializedName("proposicaoRelacionada_")
    val proposicaoRelacionada: ProposicaoResumida?,
    @SerializedName("proposicao_")
    val proposicao: ProposicaoResumida,
    val regime: String,
    val relator: Relator,
    val situacaoItem: String?,
    val textoParecer: String,
    val titulo: String,
    val topico: String,
    val uriVotacao: String?
) {
    val id = "$ordem-${proposicao.id}"

    fun getProposicaoId(): Int {
        return proposicaoRelacionada?.id ?: proposicao.id
    }
    fun getEmenta(): String {
        return proposicaoRelacionada?.ementa ?: proposicao.ementa
    }
    fun getProposicaoResumida(): ProposicaoResumida {
        return proposicaoRelacionada ?: proposicao
    }

    companion object {
        fun getMock(
            topico: String,
            titulo: String,
            ementa: String
        ) = Pauta(
            codRegime = 1,
            ordem = 1,
            proposicaoRelacionada = ProposicaoResumida.getMock(ementa),
            proposicao = ProposicaoResumida.getMock(ementa),
            regime = "regime",
            relator = Relator.getMock(),
            situacaoItem = "situacaoItem",
            textoParecer = "textoParecer",
            titulo = titulo,
            topico = topico,
            uriVotacao = "uriVotacao"
        )
    }
}