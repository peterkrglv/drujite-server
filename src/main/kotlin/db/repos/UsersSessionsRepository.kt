package db.repos

import models.SessionModel
import java.util.*

interface UsersSessionsRepository {
    suspend fun addUserSession(userId: UUID, sessionId: Int, characterId: Int? = null): Boolean
    suspend fun addCharacter(userId: UUID, sessionId: Int, characterId: Int): Boolean
    suspend fun deleteUserSession(userId: UUID, sessionId: Int): Boolean
    suspend fun deleteCharacter(userId: UUID, sessionId: Int, characterId: Int): Boolean
    suspend fun getCharacterId(userId: UUID, sessionId: Int): Int?
    suspend fun getUsersSessions(userId: UUID): List<SessionModel>
}