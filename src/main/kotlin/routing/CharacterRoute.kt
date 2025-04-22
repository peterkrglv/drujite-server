package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.CharacterModel
import requests.AddCharacterRequest
import requests.CharacterIdRequest
import responces.CharacterResponse
import responces.IdResponse
import services.CharacterService

fun Route.characterRoute(
    characterService: CharacterService
) {
    authenticate {
        get() {
            val request = call.receive<CharacterIdRequest>()
            val character = characterService.getCharacter(request.characterId)
            if (character == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, character.toResponse())
            }
        }

        post {
            val request = call.receive<AddCharacterRequest>()
            val character = CharacterModel(
                id = 0,
                name = request.name,
                story = request.story,
                clanId = request.clanId,
                imageUrl = request.image
            )
            val characterId = characterService.addCharacter(character)
            call.respond(HttpStatusCode.Created, IdResponse(characterId))
        }

        delete {
            val request = call.receive<CharacterIdRequest>()
            val result = characterService.deleteCharacter(request.characterId)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/withClanAndUser") {
            val request = call.receive<CharacterIdRequest>()
            val character = characterService.getCharacterWithClanAndUser(request.characterId)
            if (character == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, character)
            }
        }
    }
}

private fun CharacterModel.toResponse() = CharacterResponse(
    id = this.id,
    name = this.name,
    story = this.story,
    clanId = this.clanId,
    imageUrl = this.imageUrl
)