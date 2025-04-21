package db.repos

import models.CharacterModel

interface CharacterRepository {
    suspend fun add(character: CharacterModel): Int
    suspend fun get(id: Int): CharacterModel?
    suspend fun delete(id: Int): Boolean
}