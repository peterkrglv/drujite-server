package db.repos_impls

import db.mapping.CharacterDAO
import db.mapping.suspendTransaction
import db.repos.CharacterRepository
import models.CharacterModel

class CharacterRepositoryImpl: CharacterRepository {
    override suspend fun add(character: CharacterModel): Boolean {
        return suspendTransaction {
            CharacterDAO.new {
                name = character.name
                story = character.story
                clanId = character.clanId
                imageUrl = character.imageUrl
            }.let { it.id.value > 0 }
        }
    }

    override suspend fun get(id: Int): CharacterModel? {
        return suspendTransaction {
            CharacterDAO.findById(id)?.let {
                CharacterModel(
                    id = it.id.value,
                    name = it.name,
                    story = it.story,
                    clanId = it.clanId,
                    imageUrl = it.imageUrl
                )
            }
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return suspendTransaction {
            CharacterDAO.findById(id)?.delete() != null
        }
    }
}