package db.mapping

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SessionsClansTable: IntIdTable("sessions_clans") {
    val sessionId = reference("session_id", SessionTable)
    val clanId = reference("clan_id", ClanTable)
}

class SessionClansDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SessionClansDAO>(SessionsClansTable)

    var sessionId by SessionTable.id
    var clanId by ClanTable.id
}