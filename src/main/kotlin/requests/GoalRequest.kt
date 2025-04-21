package requests

import kotlinx.serialization.Serializable

@Serializable
data class GoalRequest (
    val goalId: Int
)