package models

data class GoalModel (
    val id: Int,
    val characterId: Int,
    val name: String,
    val isCompleted: Boolean
)