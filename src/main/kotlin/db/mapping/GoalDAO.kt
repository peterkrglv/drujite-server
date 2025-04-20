package db.mapping

import models.GoalModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GoalTable: IntIdTable("goals") {
    val characterId = reference("character_id", CharacterTable)
    val goal = varchar("goal", 255)
    val isComplete = bool("is_complete")
}

class GoalDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GoalDAO>(GoalTable)

    var characterId by CharacterTable.id
    var goal by GoalTable.goal
    var isComplete by GoalTable.isComplete
}

fun daoToModel(dao: GoalDAO) = GoalModel(
    id = dao.id.value,
    characterId = dao.characterId.value,
    goal = dao.goal,
    isComplete = dao.isComplete
)