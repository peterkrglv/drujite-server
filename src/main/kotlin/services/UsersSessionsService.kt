package services

import db.repos.UsersSessionsRepository
import java.util.*

class UsersSessionsService(
    private val usersSessionsRepository: UsersSessionsRepository
) {
    suspend fun addUserSession(
        userId: String,
        sessionId: Int,
        characterId: Int? = null
    ) = usersSessionsRepository.addUserSession(
        userId = UUID.fromString(userId),
        sessionId = sessionId,
        characterId = characterId
    )
    suspend fun addCharacter(
        userId: String,
        sessionId: Int,
        characterId: Int
    ) = usersSessionsRepository.addCharacter(
        userId = UUID.fromString(userId),
        sessionId = sessionId,
        characterId = characterId
    )
    suspend fun deleteUserSession(
        userId: String,
        sessionId: Int
    ) = usersSessionsRepository.deleteUserSession(
        userId = UUID.fromString(userId),
        sessionId = sessionId
    )
    suspend fun deleteCharacter(
        userId: String,
        sessionId: Int,
        characterId: Int
    ) = usersSessionsRepository.deleteCharacter(
        userId = UUID.fromString(userId),
        sessionId = sessionId,
        characterId = characterId
    )
    suspend fun getCharacterId(
        userId: String,
        sessionId: Int
    ) = usersSessionsRepository.getCharacterId(
        userId = UUID.fromString(userId),
        sessionId = sessionId
    )
    suspend fun getUsersSessions(
        userId: String
    ) = usersSessionsRepository.getUsersSessions(
        userId = UUID.fromString(userId),
    )
}