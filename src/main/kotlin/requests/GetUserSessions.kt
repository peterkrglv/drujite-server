package requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUserSessions (
    val token: String
)