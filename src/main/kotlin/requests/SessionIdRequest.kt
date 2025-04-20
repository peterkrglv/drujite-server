package requests

import kotlinx.serialization.Serializable

@Serializable
data class SessionIdRequest(
    val sessionId: Int
)