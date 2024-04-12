package com.claudiogalvaodev.camaraaberta.data.model.proposition

data class Author(
    val codTipo: Int,
    val nome: String,
    val ordemAssinatura: Int,
    val proponente: Int,
    val tipo: String,
    val uri: String
)