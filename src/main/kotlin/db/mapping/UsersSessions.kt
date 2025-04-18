package db.mapping

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UsersSessionsTable : IntIdTable("users_sessions") {
    val userId = uuid("user_id").references(UserTable.id)
    val sessionId = integer("session_id").references(SessionTable.id)
}

class UsersSessionsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UsersSessionsDAO>(UsersSessionsTable)
    var userId by UsersSessionsTable.userId
    var sessionId by UsersSessionsTable.sessionId
}