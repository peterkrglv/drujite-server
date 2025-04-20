package requests

import kotlinx.serialization.Serializable

@Serializable
data class UserSession (
    val sessionId: Int
)