package com.example.camaraabertaapp.datasource.references.dto

import com.example.camaraabertaapp.datasource.references.model.ThemeModel
import com.google.gson.annotations.SerializedName

data class ThemeDto(
    @SerializedName(value = "cod")
    val id: String,
    @SerializedName(value = "sigla")
    val acronym: String,
    @SerializedName(value = "nome")
    val name: String,
    @SerializedName(value = "descricao")
    val description: String
)

fun ThemeDto.toModel() = ThemeModel(
    id = this.id,
    name = this.name
)