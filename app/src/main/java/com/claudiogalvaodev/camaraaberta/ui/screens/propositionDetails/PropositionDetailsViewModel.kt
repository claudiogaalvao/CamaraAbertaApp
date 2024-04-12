package com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.camaraaberta.data.model.deputados.Deputado
import com.claudiogalvaodev.camaraaberta.data.model.proposition.Author
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

    private val _authors = MutableStateFlow<List<Deputado>>(emptyList())
    val authors: StateFlow<List<Deputado>> = _authors

    private val _ultimoRelator = MutableStateFlow<Deputado?>(null)
    val ultimoRelator: StateFlow<Deputado?> = _ultimoRelator

    init {
        getProposition(propositionId)
        getAuthors(propositionId)
    }

    private fun getProposition(propositionId: Int) = viewModelScope.launch {
        repository.getProposition(propositionId).getOrNull()?.let { proposition ->
            _proposition.value = proposition
            proposition.statusProposicao.getIdUltimoRelator()?.let { ultimoRelatorId ->
                getUltimoRelator(ultimoRelatorId)
            }
        }
    }

    private fun getAuthors(propositionId: Int) = viewModelScope.launch {
        repository.getPropositionAuthors(propositionId).getOrNull()?.let { deputados ->
            _authors.value = deputados
        }
    }

    private fun getUltimoRelator(id: Int) = viewModelScope.launch {
        repository.getUltimoRelator(id).getOrNull()?.let { deputado ->
            _ultimoRelator.value = deputado
        }
    }

}