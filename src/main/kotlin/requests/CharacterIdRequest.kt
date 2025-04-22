package requests

import kotlinx.serialization.Serializable

@Serializable
data class CharacterIdRequest (
    val characterId: Int
)