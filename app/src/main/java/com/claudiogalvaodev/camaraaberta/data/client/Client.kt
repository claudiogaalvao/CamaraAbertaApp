package com.claudiogalvaodev.camaraaberta.data.client

import com.claudiogalvaodev.camaraaberta.data.model.EventResponse
import com.claudiogalvaodev.camaraaberta.data.model.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Client {

    @GET("eventos")
    suspend fun getEvents(
        @Query ("ordem") sort: String = "ASC",
        @Query ("ordenarPor") sortBy: String = "dataHoraInicio",
        @Query ("dataInicio") startDate: String,
        @Query ("dataFim") endDate: String
    ): EventsResponse

    @GET("eventos/{id}")
    suspend fun getEvent(
        @Path ("id") id: Int
    ): EventResponse

}