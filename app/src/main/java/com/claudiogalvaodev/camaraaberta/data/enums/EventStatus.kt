package com.claudiogalvaodev.camaraaberta.data.enums

import androidx.compose.ui.graphics.Color

enum class EventStatus(val situacao: String, val text: String, val color: Color) {
    NOT_CONFIRMED("Não Confirmada", "Não Confirmada", Color(0xFF22A87E)),
    CALLED("Convocada", "Convocada", Color(0xFF22A87E)),
    IN_PROGRESS("Em Andamento", "Em Andamento",Color(0xFF22A87E)),
    FINISHED("Encerrada", "Encerrada",Color(0xFF22A87E).copy(alpha = 0.3f)),
    CANCELED("Cancelada", "Cancelada",Color.Red),
    SUSPENDED("Suspensa", "Suspensa",Color.Red),
    FINISHED_TERM("Encerrada (Termo)", "Encerrada",Color(0xFF22A87E).copy(alpha = 0.3f)),
    FINISHED_FINAL("Encerrada (Final)", "Encerrada",Color(0xFF22A87E).copy(alpha = 0.3f)),
    FINISHED_NOTICE("Encerrada (Comunicado)", "Encerrada",Color(0xFF22A87E).copy(alpha = 0.3f)),
    SCHEDULED("Agendada", "Agendada", Color(0xFF22A87E)),
    UNKNOWN("Desconhecida", "Desconhecida", Color(0xFF22A87E));

    fun isFinished(): Boolean {
        return when (this) {
            CANCELED, SUSPENDED, FINISHED, FINISHED_TERM, FINISHED_FINAL, FINISHED_NOTICE -> true
            else -> false
        }
    }

    fun isInProgress(): Boolean {
        return when (this) {
            IN_PROGRESS -> true
            else -> false
        }
    }

    companion object {
        fun get(value: String): EventStatus = entries.firstOrNull {
            it.situacao == value
        } ?: UNKNOWN
    }
}
