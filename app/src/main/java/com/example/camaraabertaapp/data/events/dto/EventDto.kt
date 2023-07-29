package com.example.camaraabertaapp.data.events.dto

import com.example.camaraabertaapp.data.events.model.EventsModel
import com.example.camaraabertaapp.util.toLocalDateTime
import com.google.gson.annotations.SerializedName

data class EventDto(
    val id: Int,
    @SerializedName(value = "descricao")
    val description: String,
    @SerializedName(value = "descricaoTipo")
    val type: String,
    @SerializedName(value = "dataHoraInicio")
    val startAt: String,
    @SerializedName(value = "dataHoraFim")
    val finishAt: String?,
    @SerializedName(value = "situacao")
    val status: String,
    @SerializedName(value = "localCamara")
    val chamberLocation: ChamberLocation,
    @SerializedName(value = "localExterno")
    val externalLocation: String?,
    val agencies: List<AgencyDto>,
    val uri: String,
    val registrationUrl: String?
)

fun EventDto.toModel() = EventsModel(
    id = this.id,
    agencies = this.agencies.map { it.toModel() },
    description = this.description,
    type = this.type,
    status = this.status,
    startAt = this.startAt.toLocalDateTime(),
    finishAt = this.finishAt?.toLocalDateTime(),
    registrationUrl = this.registrationUrl
)