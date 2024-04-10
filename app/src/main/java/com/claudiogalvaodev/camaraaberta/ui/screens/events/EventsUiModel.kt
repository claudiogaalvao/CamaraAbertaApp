package com.claudiogalvaodev.camaraaberta.ui.screens.events

import com.claudiogalvaodev.camaraaberta.data.model.event.Event

data class EventsUiModel(
    val events: List<Event> = emptyList(),
    val eventsInProgress: List<Event> = emptyList()
)