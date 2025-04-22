package requests

import kotlinx.serialization.Serializable

@Serializable
data class EventIdRequest (
    val eventId: Int
)