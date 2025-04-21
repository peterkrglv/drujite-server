package requests

import kotlinx.serialization.Serializable

@Serializable
data class CharacterRequest (
    val characterId: Int
)