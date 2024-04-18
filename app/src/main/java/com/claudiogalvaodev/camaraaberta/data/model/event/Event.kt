package com.claudiogalvaodev.camaraaberta.data.model.event

import com.claudiogalvaodev.camaraaberta.data.enums.EventStatus
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
        get() = descricao.substringBefore("\r")

    val videoId: String?
        get() = urlRegistro?.substringAfter("v=")

    val isInProgress: Boolean
        get() = EventStatus.get(situacao).isInProgress()

    val hasUrlRegistro: Boolean
        get() = urlRegistro != null

    val isFinished: Boolean
        get() = dataHoraFim != null || dataHoraInicio.toLocalDateTime().isNotToday() || EventStatus.get(situacao).isFinished()

    val local: String
        get() = localExterno ?: localCamara.nome

    val isDeliberativo: Boolean
        get() = descricaoTipo.contentEquals("Sessão Deliberativa") ||
                descricaoTipo.contentEquals("Reunião Deliberativa") ||
                descricaoTipo.contentEquals("Audiência Pública e Deliberação") ||
                descricaoTipo.contentEquals("Tomada de Depoimento e Deliberação")


}
