package models

import responces.CharacterResponse

data class CharacterModel (
    val id: Int,
    val name: String,
    val story: String,
    val clanId: Int,
    val imageUrl: String? = null
)

