package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails

import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.pauta.Pauta

data class EventDetailsUiModel(
    val event: Event,
    val pauta: List<Pauta> = emptyList()
)
