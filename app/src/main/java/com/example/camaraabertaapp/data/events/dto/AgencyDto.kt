package com.example.camaraabertaapp.data.events.dto

import com.example.camaraabertaapp.data.events.model.AgencyModel
import com.google.gson.annotations.SerializedName

data class AgencyDto(
    val id: Int,
    @SerializedName(value = "nome")
    val name: String,
    @SerializedName(value = "nomeResumido")
    val shortName: String,
    @SerializedName(value = "nomePublicacao")
    val publicationName: String,
    @SerializedName(value = "sigla")
    val acronym: String,
    @SerializedName(value = "apelido")
    val surname: String,
    @SerializedName(value = "codTipoOrgao")
    val idAgencyType: Int,
    @SerializedName(value = "tipoOrgao")
    val agencyType: String,
    val uri: String
)

fun AgencyDto.toModel() = AgencyModel(
    id = this.id,
    shortName = this.shortName
)