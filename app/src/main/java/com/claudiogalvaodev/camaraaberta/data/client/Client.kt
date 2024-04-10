package com.claudiogalvaodev.camaraaberta.data.client

import com.claudiogalvaodev.camaraaberta.data.model.common.ApiResponse
import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.pauta.Pauta
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
    ): ApiResponse<List<Event>>

    @GET("eventos/{id}")
    suspend fun getEvent(
        @Path ("id") id: Int
    ): ApiResponse<Event>

    @GET("eventos/{id}/pauta")
    suspend fun getPauta(
        @Path ("id") id: Int
    ): ApiResponse<List<Pauta>>

}