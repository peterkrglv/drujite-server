package db.repos

import models.GoalModel

interface GoalRepository {
    suspend fun add(goal: GoalModel): GoalModel
    suspend fun get(id: Int): GoalModel
    suspend fun delete(id: Int): Boolean
}