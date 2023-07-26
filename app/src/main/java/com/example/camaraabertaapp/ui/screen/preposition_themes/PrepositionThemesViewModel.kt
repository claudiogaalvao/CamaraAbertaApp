package com.example.camaraabertaapp.ui.screen.preposition_themes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camaraabertaapp.data.preferences.IPreferences
import com.example.camaraabertaapp.data.references.repository.IReferencesRepository
import com.example.camaraabertaapp.util.ONBOARD_FINISHED_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract repository and preference to UseCases
@HiltViewModel
class PrepositionThemesViewModel @Inject constructor(
    private val repository: IReferencesRepository,
    private val preferences: IPreferences
) : ViewModel() {

    var state by mutableStateOf(PrepositionThemesState())
        private set

    // TODO Move to other place
    var shouldInitFromOnboard by mutableStateOf(true)
        private set

    init {
        getPrepositionThemes()
        getOnboardingFinished()
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

    fun saveOnboardFinished() {
        viewModelScope.launch {
            preferences.setIsOnboardFinished(ONBOARD_FINISHED_KEY, true)
        }
    }

    // TODO Move to other place
    fun getOnboardingFinished() {
        viewModelScope.launch {
            val isOnboardFinished = preferences.getIsOnboardFinished(ONBOARD_FINISHED_KEY) ?: true
            shouldInitFromOnboard = isOnboardFinished.not()
        }
    }

    companion object {
        private const val MINIMUM_SELECTION_THEMES = 3
    }

}