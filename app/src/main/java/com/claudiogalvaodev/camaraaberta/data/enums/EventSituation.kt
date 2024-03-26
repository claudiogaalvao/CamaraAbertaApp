package com.claudiogalvaodev.camaraaberta.data.enums

enum class EventSituation(situacao: String) {
    NOT_CONFIRMED("Não Confirmada"),
    CALLED("Convocada"),
    IN_PROGRESS("Em Andamento"),
    FINISHED("Encerrada"),
    CANCELED("Cancelada"),
    SUSPENDED("Suspensa"),
    FINISHED_TERM("Encerrada (Termo)"),
    FINISHED_FINAL("Encerrada (Final)"),
    FINISHED_NOTICE("Encerrada (Comunicado)"),
    SCHEDULED("Agendada"),
    UNKNOWN("Desconhecida")
}
