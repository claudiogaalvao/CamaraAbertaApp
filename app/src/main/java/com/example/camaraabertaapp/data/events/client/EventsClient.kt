package com.example.camaraabertaapp.data.events.client

import com.example.camaraabertaapp.data.events.dto.EventsResponseDto
import retrofit2.http.GET

interface EventsClient {

    @GET("eventos")
    suspend fun getAllEvents(): EventsResponseDto

}