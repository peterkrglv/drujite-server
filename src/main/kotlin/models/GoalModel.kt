package models

data class GoalModel (
    val id: Int,
    val characterId: Int,
    val goal: String,
    val isComplete: Boolean
)