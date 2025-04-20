package db.repos_impls

import db.mapping.*
import db.repos.UsersSessionsRepository
import models.SessionModel
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsersSessionsRepositoryImpl : UsersSessionsRepository {
    override suspend fun addUserSession(userId: UUID, sessionId: Int, characterId: Int?): Boolean {
        return suspendTransaction {
            val existingSession = UsersSessionsDAO.find { UsersSessionsTable.userId eq userId and (UsersSessionsTable.sessionId eq sessionId) }.firstOrNull()
            if (existingSession != null) {
                if (characterId != null) {
                    existingSession.characterId = characterId
                    return@suspendTransaction true
                }
                else {
                    return@suspendTransaction false
                }
            } else {
                UsersSessionsDAO.new {
                    this.userId = userId
                    this.sessionId = sessionId
                    this.characterId = characterId
                }
                return@suspendTransaction true
            }
        }
    }


    override suspend fun addCharacter(userId: UUID, sessionId: Int, characterId: Int): Boolean {
        return suspendTransaction {
            val existingSession = UsersSessionsDAO.find { UsersSessionsTable.userId eq userId and (UsersSessionsTable.sessionId eq sessionId) }.firstOrNull()
            if (existingSession != null) {
                existingSession.characterId = characterId
                return@suspendTransaction true
            } else {
                return@suspendTransaction false
            }
        }
    }

    override suspend fun deleteUserSession(userId: UUID, sessionId: Int): Boolean {
        return suspendTransaction {
            val existingSession = UsersSessionsDAO.find { UsersSessionsTable.userId eq userId and (UsersSessionsTable.sessionId eq sessionId) }.firstOrNull()
            if (existingSession != null) {
                existingSession.delete()
                return@suspendTransaction true
            } else {
                return@suspendTransaction false
            }
        }
    }

    override suspend fun deleteCharacter(userId: UUID, sessionId: Int, characterId: Int): Boolean {
        return suspendTransaction {
            val existingSession = UsersSessionsDAO.find { UsersSessionsTable.userId eq userId and (UsersSessionsTable.sessionId eq sessionId) and (UsersSessionsTable.characterId eq characterId) }.firstOrNull()
            if (existingSession != null) {
                existingSession.characterId = null
                return@suspendTransaction true
            } else {
                return@suspendTransaction false
            }
        }
    }

    override suspend fun getCharacterId(userId: UUID, sessionId: Int): Int? {
        return suspendTransaction {
            val existingSession = UsersSessionsDAO.find { UsersSessionsTable.userId eq userId and (UsersSessionsTable.sessionId eq sessionId) }.firstOrNull()
            return@suspendTransaction existingSession?.characterId
        }
    }

    override suspend fun getUsersSessions(userId: UUID): List<SessionModel> {
        return transaction {
            UsersSessionsDAO.find { UsersSessionsTable.userId eq userId }
                .mapNotNull { it.sessionId.let(SessionDAO::findById)?.let(::daoToModel) }
        }
    }
}