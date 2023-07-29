package com.example.camaraabertaapp.ui.screen.events

import com.example.camaraabertaapp.data.events.model.EventsModel

data class EventsState(
    val events: List<EventsModel>,
    val isLoading: Boolean = false,
    val error: String = ""
)