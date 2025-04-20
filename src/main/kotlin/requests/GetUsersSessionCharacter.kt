package requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUsersSessionCharacter(
    val sessionId: Int
)