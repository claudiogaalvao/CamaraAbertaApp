package com.example.camaraabertaapp.data.events.dto

import com.example.camaraabertaapp.data.common.dto.LinkDto
import com.google.gson.annotations.SerializedName

data class EventsResponseDto(
    @SerializedName(value = "dados")
    val data: List<EventDto>,
    val links: List<LinkDto>
)