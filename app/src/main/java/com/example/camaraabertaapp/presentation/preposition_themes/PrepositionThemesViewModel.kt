package com.example.camaraabertaapp.presentation.preposition_themes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camaraabertaapp.datasource.references.repository.IReferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrepositionThemesViewModel @Inject constructor(
    private val repository: IReferencesRepository
) : ViewModel() {

    private val _state = mutableStateOf(PrepositionThemesState())
    val state: State<PrepositionThemesState> = _state

    init {
        getPrepositionThemes()
    }

    private fun getPrepositionThemes() {
        viewModelScope.launch {
            _state.value = PrepositionThemesState(isLoading = true)
            val themesResult = repository.getAllPrepositionThemes()
            if (themesResult.isSuccess) {
                _state.value = PrepositionThemesState(
                    themes = themesResult.getOrDefault(emptyList())
                )
            } else {
                val error = themesResult.exceptionOrNull()
                _state.value = PrepositionThemesState(
                    error = error?.message ?: ""
                )
            }
        }
    }

}