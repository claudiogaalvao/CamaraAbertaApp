package com.claudiogalvaodev.camaraaberta.ui.screens.events

import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.pauta.ProposicaoResumida
import com.claudiogalvaodev.camaraaberta.ui.screens.common.RequestState

data class EventsUiModel(
    val events: List<Event> = emptyList(),
    val eventsInProgress: List<Event> = emptyList(),
    val projetosDeLeiDoDia: RequestState<List<ProposicaoResumida>> = RequestState.Loading
)
