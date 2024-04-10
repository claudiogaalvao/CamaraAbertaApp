package com.claudiogalvaodev.camaraaberta.data.model.pauta

import com.google.gson.annotations.SerializedName

data class Pauta(
    val codRegime: Int,
    val ordem: Int,
    @SerializedName("proposicaoRelacionada_")
    val proposicaoRelacionada: Proposicao?,
    @SerializedName("proposicao_")
    val proposicao: Proposicao,
    val regime: String,
    val relator: Relator,
    val situacaoItem: String?,
    val textoParecer: String,
    val titulo: String,
    val topico: String,
    val uriVotacao: String?
) {
    val id = "$ordem-${proposicao.id}"
    fun getLongerEmenta(): String {
        return proposicaoRelacionada?.let { relacionada ->
            if (proposicao.ementa.length > relacionada.ementa.length) {
                proposicao.ementa
            } else relacionada.ementa
        } ?: proposicao.ementa
    }
    companion object {
        fun getMock(
            topico: String,
            titulo: String,
            ementa: String
        ) = Pauta(
            codRegime = 1,
            ordem = 1,
            proposicaoRelacionada = Proposicao.getMock(ementa),
            proposicao = Proposicao.getMock(ementa),
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