package com.claudiogalvaodev.camaraaberta.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.pauta.ProposicaoResumida
import com.claudiogalvaodev.camaraaberta.data.repository.EventsRepository
import com.claudiogalvaodev.camaraaberta.ui.screens.common.RequestState
import com.claudiogalvaodev.camaraaberta.ui.screens.common.update
import com.claudiogalvaodev.camaraaberta.utils.toDateString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val DEFAULT_ERROR_MESSAGE = "Erro desconhecido"

class EventsViewModel(
    private val eventsRepository: EventsRepository
): ViewModel() {

    private val _state = MutableStateFlow<RequestState<EventsUiModel>>(RequestState.Loading)
    val state: StateFlow<RequestState<EventsUiModel>> = _state

    private var currentJobProjetosDeLeiDoDia: Job? = null

    private val _currentDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val currentDate: Flow<LocalDate> = _currentDate.onEach { date ->
         getEvents(date)
    }

    private fun getEvents(date: LocalDate) = viewModelScope.launch {
        _state.value = RequestState.Loading
        if (currentJobProjetosDeLeiDoDia?.isActive == true) {
            currentJobProjetosDeLeiDoDia?.cancel()
        }
        try {
            val events = eventsRepository.getEvents(date.toDateString()).getOrThrow()
            _state.value = if (events.isEmpty()) {
                RequestState.Empty
            } else {
                RequestState.Success(
                    EventsUiModel(
                        events = events.sortedBy { it.dataHoraInicio },
                        eventsInProgress = events.filter { it.isInProgress && it.hasUrlRegistro }
                    )
                )
            }
            currentJobProjetosDeLeiDoDia = getProjetosDeLeiDoDia(events)
        } catch (e: Exception) {
            _state.emit(RequestState.Error(e.message ?: DEFAULT_ERROR_MESSAGE))
        }
    }

    private fun getProjetosDeLeiDoDia(events: List<Event>) = viewModelScope.launch {
        val eventosDeliberativos = events.filter { it.isDeliberativo }
        val projetosDeLeiDoDia = mutableListOf<ProposicaoResumida>()
        _state.value = if (eventosDeliberativos.isEmpty()) {
            _state.value.update { data ->
                data.copy(projetosDeLeiDoDia = RequestState.Empty)
            }
        } else {
            eventosDeliberativos.forEach {  evento ->
                _state.value = _state.value.update { data ->
                    data.copy(projetosDeLeiDoDia = RequestState.Loading)
                }
                val pautaResult = eventsRepository.getPauta(evento.id)
                val pauta = pautaResult.getOrNull()
                pauta?.let {
                    it.forEach {
                        val proposicao = it.getProposicaoResumida()
                        if (proposicao.isProjetoDeLei()) {
                            projetosDeLeiDoDia.add(proposicao)
                        }
                    }
                }
            }
            _state.value.update { data ->
                data.copy(projetosDeLeiDoDia = if (projetosDeLeiDoDia.isEmpty()) {
                    RequestState.Empty
                } else {
                    RequestState.Success(projetosDeLeiDoDia)
                })
            }
        }
    }

    fun resetCurrentDate() {
        _currentDate.value = LocalDate.now()
    }

    fun selectDate(date: LocalDate) {
        _currentDate.value = date
    }

    fun selectNextDate() {
        _currentDate.value = _currentDate.value.plusDays(1)
    }

    fun selectPreviousDate() {
        _currentDate.value = _currentDate.value.minusDays(1)
    }

}