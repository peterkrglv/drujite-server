package requests

import kotlinx.serialization.Serializable

@Serializable
data class ClanIdRequest (
    val clanId: Int
)