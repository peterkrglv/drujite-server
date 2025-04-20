package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.CharacterModel
import requests.AddUserSessionCharacter
import requests.GetUsersSessionCharacter
import requests.SessionIdRequest
import responces.CharacterResponse
import services.JwtService
import services.UsersSessionsService

fun Route.usersCharactersRoute(
    jwtService: JwtService,
    usersSessionsService: UsersSessionsService
) {
    authenticate {
        get {
            val characterRequest = call.receive<GetUsersSessionCharacter>()
            val principal = call.principal<JWTPrincipal>()
            val userId =
                principal?.let { jwtService.extractId(it) } ?: return@get call.respond(HttpStatusCode.Unauthorized)
            val character = usersSessionsService.getCharacter(
                userId = userId,
                sessionId = characterRequest.sessionId
            )
            if (character == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, character.toResponse())
            }
        }

        post {
            val characterRequest = call.receive<AddUserSessionCharacter>()
            val principal = call.principal<JWTPrincipal>()
            val userId =
                principal?.let { jwtService.extractId(it) } ?: return@post call.respond(HttpStatusCode.Unauthorized)
            usersSessionsService.addCharacter(
                userId = userId,
                sessionId = characterRequest.sessionId,
                characterId = characterRequest.characterId
            )
            call.respond(HttpStatusCode.Created)
        }

        get("/user-all") {
            val principal = call.principal<JWTPrincipal>()
            val userId =
                principal?.let { jwtService.extractId(it) } ?: return@get call.respond(HttpStatusCode.Unauthorized)
            val characters = usersSessionsService.getCharacters(userId)
            call.respond(HttpStatusCode.OK, characters.map { it.toResponse() })

        }

        get("session-all") {
            val request = call.receive<SessionIdRequest>()
            val characters = usersSessionsService.getSessionsCharacters(request.sessionId)
            call.respond(HttpStatusCode.OK, characters.map { it.toResponse() })
        }

        delete {
            val characterRequest = call.receive<AddUserSessionCharacter>()
            val principal = call.principal<JWTPrincipal>()
            val userId =
                principal?.let { jwtService.extractId(it) } ?: return@delete call.respond(HttpStatusCode.Unauthorized)
            val result = usersSessionsService.deleteCharacter(
                userId = userId,
                sessionId = characterRequest.sessionId,
                characterId = characterRequest.characterId
            )
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

private fun CharacterModel.toResponse() =
    CharacterResponse(
        id = id,
        name = name,
        story = story,
        clanId = clanId,
        imageUrl = imageUrl
    )