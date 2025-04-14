package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.SessionModel
import requests.GetSession
import requests.SessionRequest
import responces.SessionResponse
import services.JwtService
import services.SessionService

fun Route.sessionRoute(
    jwtService: JwtService,
    sessionService: SessionService,
) {
    fun Route.sessionRoute(
        jwtService: JwtService,
        sessionService: SessionService,
    ) {
        authenticate {
            get("/{id}") {
                val request = call.receive<GetSession>()
                val session = sessionService.getSessionById(request)
                if (session == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(HttpStatusCode.OK, session.toResponse())
            }

            get("/user-sessions") {
                val principal = call.principal<JWTPrincipal>()
                    ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val userId = principal.payload.getClaim("userId").asString()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                val sessions = sessionService.getSessionsByUserId(userId)
                call.respond(HttpStatusCode.OK, sessions.map { it.toResponse() })
            }

            post {
                val sessionRequest = call.receive<SessionRequest>()
                sessionService.addSession(sessionRequest)
                call.respond(HttpStatusCode.Created, sessionRequest.toResponse())
            }

            delete {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)

                sessionService.deleteSession(id)
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}

private fun SessionRequest.toResponse(): SessionResponse
    = SessionResponse(
        id = 0,
        name = this.name,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate,
        imageUrl = this.imageUrl
    )

private fun SessionModel.toResponse(): SessionResponse {
    return SessionResponse(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        startDate = this.startDate,
        endDate = this.endDate,
        imageUrl = this.imageUrl
    )
}
