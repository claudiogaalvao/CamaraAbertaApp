package com.example.camaraabertaapp.presentation.preposition_themes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var state by mutableStateOf(PrepositionThemesState())
        private set

    init {
        getPrepositionThemes()
    }

    private fun getPrepositionThemes() {
        viewModelScope.launch {
            state = PrepositionThemesState(isLoading = true)
            val themesResult = repository.getAllPrepositionThemes()
            state = if (themesResult.isSuccess) {
                PrepositionThemesState(
                    themes = themesResult.getOrDefault(emptyList())
                )
            } else {
                val error = themesResult.exceptionOrNull()
                PrepositionThemesState(
                    error = error?.message ?: ""
                )
            }
        }
    }

    fun toggleSelection(idTheme: String) {
        val newList = state.themes.toMutableList()
        val index = newList.indexOfFirst { it.id == idTheme }
        newList[index] = newList[index].copy(isSelected = newList[index].isSelected.not())
        val shouldEnableButton = newList.filter { it.isSelected }.count() >= MINIMUM_SELECTION_THEMES
        state = PrepositionThemesState(themes = newList, isButtonEnabled = shouldEnableButton)
    }

    companion object {
        private const val MINIMUM_SELECTION_THEMES = 3
    }

}