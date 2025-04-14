package db.repos

import models.SessionModel
import java.util.*

interface SessionRepository {
    suspend fun getById(id: Int): SessionModel?
    suspend fun addSession(session: SessionModel): SessionModel
    suspend fun deleteSession(id: Int): Boolean
    suspend fun getSessionsByUserId(userId: UUID): List<SessionModel>
}