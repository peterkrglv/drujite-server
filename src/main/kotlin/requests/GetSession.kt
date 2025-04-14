package requests

import kotlinx.serialization.Serializable

@Serializable
data class GetSession(
    val id: Int
)