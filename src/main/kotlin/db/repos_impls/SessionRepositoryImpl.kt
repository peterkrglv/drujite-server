package db.repos_impls

import db.mapping.SessionDAO
import db.mapping.UsersSessionsDAO
import db.mapping.UsersSessionsTable
import db.mapping.daoToModel
import db.repos.SessionRepository
import models.SessionModel
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SessionRepositoryImpl : SessionRepository {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME
    override suspend fun getById(id: Int): SessionModel? {
        return transaction {
            SessionDAO.findById(id)?.let(::daoToModel)
        }
    }

    override suspend fun addSession(session: SessionModel): SessionModel {
        return transaction {
            SessionDAO.new {
                name = session.name
                description = session.description
                startDate = LocalDateTime.parse(session.startDate, formatter)
                endDate = LocalDateTime.parse(session.endDate, formatter)
                imageUrl = session.imageUrl
            }.let(::daoToModel)
        }
    }

    override suspend fun deleteSession(id: Int): Boolean {
        return transaction {
            SessionDAO.findById(id)?.delete() != null
        }
    }

    override suspend fun getSessionsByUserId(userId: UUID): List<SessionModel> {
        return transaction {
            UsersSessionsDAO.find { UsersSessionsTable.userId eq userId }
                .mapNotNull { it.sessionId.let(SessionDAO::findById)?.let(::daoToModel) }
        }
    }
}