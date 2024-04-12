package com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.model.proposition.Proposition
import com.claudiogalvaodev.camaraaberta.data.repository.PropositionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PropositionDetailsViewModel(
    propositionId: Int,
    private val repository: PropositionsRepository
): ViewModel() {

    private val _proposition = MutableStateFlow<Proposition?>(null)
    val proposition: StateFlow<Proposition?> = _proposition

    init {
        getProposition(propositionId)
    }

    private fun getProposition(propositionId: Int) = viewModelScope.launch {
        repository.getProposition(propositionId)
            .onSuccess {
                _proposition.value = it
            }
            .onFailure {
                Log.e("Claudio", "Error getting proposition", it)
            }
    }

}