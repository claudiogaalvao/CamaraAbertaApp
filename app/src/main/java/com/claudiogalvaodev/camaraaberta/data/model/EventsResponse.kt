package com.claudiogalvaodev.camaraaberta.data.model

data class EventsResponse(
    val dados: List<Event>,
    val links: List<Link>
)