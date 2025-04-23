package db.mapping

import models.GoalModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GoalTable: IntIdTable("goals") {
    val characterId = integer("character_id").references(CharacterTable.id)
    val name = varchar("name", 255)
    val isCompleted = bool("is_completed").default(false)
}

class GoalDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GoalDAO>(GoalTable)

    var characterId by GoalTable.characterId
    var name by GoalTable.name
    var isCompleted by GoalTable.isCompleted
}

fun daoToModel(dao: GoalDAO) = GoalModel(
    id = dao.id.value,
    characterId = dao.characterId,
    name = dao.name,
    isCompleted = dao.isCompleted
)