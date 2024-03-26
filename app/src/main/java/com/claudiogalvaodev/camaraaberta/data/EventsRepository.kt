package com.claudiogalvaodev.camaraaberta.data

import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.model.Event
import java.util.Date

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
            Result.success(result.dados)
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
            Result.success(result.dados)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}