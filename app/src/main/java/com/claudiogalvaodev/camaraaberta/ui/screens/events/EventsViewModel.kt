package com.claudiogalvaodev.camaraaberta.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.EventsRepository
import com.claudiogalvaodev.camaraaberta.data.model.Event
import com.claudiogalvaodev.camaraaberta.utils.toDateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class EventsViewModel(
    private val eventsRepository: EventsRepository
): ViewModel() {

    private val _currentDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.combine(currentDate) { _, date ->
        eventsRepository
            .getEvents(date.toDateString())
            .getOrNull()
            ?.sortedBy { it.dataHoraInicio }
            ?: emptyList()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _onGoingEvents = MutableStateFlow<List<Event>>(emptyList())
    val onGoingEvents: StateFlow<List<Event>> = _onGoingEvents.combine(events) { _, allEvents ->
        allEvents
            .filter { it.isOnGoing }
            .filter { it.urlRegistro != null }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun resetCurrentDate() {
        _currentDate.value = LocalDate.now()
    }

    fun selectNextDate() {
        _currentDate.value = _currentDate.value.plusDays(1)
    }

    fun selectPreviousDate() {
        _currentDate.value = _currentDate.value.minusDays(1)
    }

}