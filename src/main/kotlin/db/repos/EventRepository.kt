package db.repos

import models.EventModel

interface EventRepository {
    suspend fun add(event: EventModel): Boolean
    suspend fun get(id: Int): EventModel
    suspend fun delete(id: Int): Boolean
}