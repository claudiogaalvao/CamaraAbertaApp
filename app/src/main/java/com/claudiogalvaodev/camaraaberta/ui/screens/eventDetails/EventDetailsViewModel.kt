package com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.repository.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    eventId: Int,
    private val eventsRepository: EventsRepository
): ViewModel() {

    private val _eventDetailsUiModel = MutableStateFlow<EventDetailsUiModel?>(null)
    val eventDetailsUiModel: StateFlow<EventDetailsUiModel?> = _eventDetailsUiModel

    init {
        getEvent(eventId)
        getPauta(eventId)
    }

    private fun getEvent(eventId: Int) = viewModelScope.launch {
        eventsRepository.getEvent(eventId).getOrNull()?.let { event ->
            _eventDetailsUiModel.value = EventDetailsUiModel(
                event = event
            )
        }
    }

    private fun getPauta(eventId: Int) = viewModelScope.launch {
        eventsRepository.getPauta(eventId).getOrNull()?.let { pauta ->
            pauta.forEach { Log.d("Claudio", "Proposta id: ${it.proposicao.id}") }
            _eventDetailsUiModel.value = _eventDetailsUiModel.value?.copy(
                pauta = pauta
            )
        }
    }

}