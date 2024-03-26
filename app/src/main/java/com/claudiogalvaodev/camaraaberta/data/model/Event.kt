package com.claudiogalvaodev.camaraaberta.data.model

import com.claudiogalvaodev.camaraaberta.utils.isNotToday
import com.claudiogalvaodev.camaraaberta.utils.toLocalDateTime

data class Event(
    val id: Int,
    val descricao: String,
    val descricaoTipo: String,
    val dataHoraFim: String?,
    val dataHoraInicio: String,
    val localCamara: Local,
    val localExterno: String?,
    val orgaos: List<Orgao>,
    val situacao: String,
    val uri: String,
    val urlRegistro: String?
) {

    val title: String
        get() = descricao.substringBefore("\\r")

    val videoId: String
        get() = urlRegistro?.substringAfter("v=") ?: ""

    val isOnGoing: Boolean
        get() = situacao.uppercase() == "EM ANDAMENTO"

    val isFinished: Boolean
        get() = dataHoraFim != null || dataHoraInicio.toLocalDateTime().isNotToday()

    val local: String
        get() = localExterno ?: localCamara.nome

}
