package services

import db.repos.CharacterRepository
import models.CharacterModel

class CharacterService(
    private val characterRepository: CharacterRepository
) {
    suspend fun getCharacter(id: Int) = characterRepository.get(id)
    suspend fun addCharacter(character: CharacterModel) = characterRepository.add(character)
    suspend fun deleteCharacter(id: Int) = characterRepository.delete(id)
}