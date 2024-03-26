package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.EventsRepository
import com.claudiogalvaodev.camaraaberta.data.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val eventsRepository: EventsRepository
): ViewModel() {

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    fun getEvent(eventId: Int) = viewModelScope.launch {
        _event.value = eventsRepository.getEvent(eventId).getOrNull()
    }

}