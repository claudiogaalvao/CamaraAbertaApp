package com.claudiogalvaodev.camaraaberta.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.EventsRepository
import com.claudiogalvaodev.camaraaberta.ui.screens.common.BaseScreenState
import com.claudiogalvaodev.camaraaberta.utils.toDateString
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

    private val _state = MutableStateFlow<BaseScreenState<EventsUiModel>>(BaseScreenState.Loading)
    val state: StateFlow<BaseScreenState<EventsUiModel>> = _state

    private val _currentDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val currentDate: Flow<LocalDate> = _currentDate.onEach { date ->
         getEvents(date)
    }

    private fun getEvents(date: LocalDate) = viewModelScope.launch {
        _state.value = BaseScreenState.Loading
        try {
            val events = eventsRepository.getEvents(date.toDateString()).getOrThrow()
            _state.value = if (events.isEmpty()) {
                BaseScreenState.Empty
            } else {
                BaseScreenState.Success(
                    EventsUiModel(
                        events = events.sortedBy { it.dataHoraInicio },
                        eventsInProgress = events.filter { it.isInProgress && it.hasUrlRegistro }
                    )
                )
            }
        } catch (e: Exception) {
            _state.value = BaseScreenState.Error(e.message ?: DEFAULT_ERROR_MESSAGE)
        }
    }

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