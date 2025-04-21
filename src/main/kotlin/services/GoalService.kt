package services

import db.repos.GoalRepository
import models.GoalModel
import requests.AddGoalRequest
import requests.GoalRequest

class GoalService(
    private val goalRepository: GoalRepository,
) {
    suspend fun getGoal(id: Int) = goalRepository.get(id)

    suspend fun addGoal(goal: AddGoalRequest) = goalRepository.add(goal.toModel())

    suspend fun deleteGoal(id: Int) = goalRepository.delete(id)

    suspend fun getCharacterGoals(characterId: Int) =
        goalRepository.getCharacterGoals(characterId)

    suspend fun updateGoalStatus(id: Int) =
        goalRepository.changeStatus(id)
}

private fun AddGoalRequest.toModel() =
    GoalModel(
        id = 0,
        characterId = this.characterId,
        name = this.name,
        isCompleted = false,
    )