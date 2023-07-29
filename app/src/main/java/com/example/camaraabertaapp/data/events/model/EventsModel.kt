package com.example.camaraabertaapp.data.events.model

import java.time.LocalDateTime

data class EventsModel(
    val id: Int,
    val agencies: List<AgencyModel>,
    val description: String,
    val type: String,
    val status: String,
    val startAt: LocalDateTime?,
    val finishAt: LocalDateTime?,
    val registrationUrl: String?
)