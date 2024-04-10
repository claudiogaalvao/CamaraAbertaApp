package com.claudiogalvaodev.camaraaberta.data.repository

import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.model.event.Event
import com.claudiogalvaodev.camaraaberta.data.model.pauta.Pauta

class EventsRepository(
    private val client: Client
) {

    suspend fun getEvents(
        date: String
    ): Result<List<Event>> {
        return try {
            val result = client.getEvents(
                startDate = date,
                endDate = date
            )
            Result.success(result.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getEvent(
        id: Int
    ): Result<Event> {
        return try {
            val result = client.getEvent(id)
            Result.success(result.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getPauta(
        eventId: Int
    ): Result<List<Pauta>> {
        return try {
            val result = client.getPauta(eventId)
            Result.success(result.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}