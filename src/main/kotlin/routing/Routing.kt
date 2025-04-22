package routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.drujite.routing.authRoute
import services.*

fun Application.configureRouting(
    userService: UserService,
    jwtService: JwtService,
    sessionService: SessionService,
    usersSessionsService: UsersSessionsService,
    characterService: CharacterService,
    goalService: GoalService,
    timeTableService: TimeTableService,
    clanService: ClanService
) {
    val v1 = "/api/v1/"
    routing {
        static("/static") {
            resources("static")
        }

        route(v1 + "user") {
            userRoute(userService, jwtService)
        }

        route(v1 + "auth") {
            authRoute(jwtService)
        }

        route(v1 + "signup") {
            signupRoute(jwtService, userService)
        }

        route(v1 + "session") {
            sessionRoute(jwtService, sessionService)
        }

        route(v1 + "users-sessions") {
            usersSessionsRoute(jwtService, usersSessionsService)
        }

        route(v1 + "users-characters") {
            usersCharactersRoute(jwtService, usersSessionsService)
        }

        route(v1 + "character") {
            characterRoute(characterService)
        }

        route (v1 + "goal") {
            goalRoute(goalService)
        }

        route(v1 + "timetable") {
            timeTableRoute(timeTableService)
        }

        route(v1 + "event") {
            eventRoute(timeTableService)
        }

        route(v1 + "clan") {
            clanRoute(clanService = clanService)
        }

        get(v1) {
            environment.log.info("Heloo")
            call.respondText(
                """
                <html>
                    <head>
                        <title>Drujite API</title>
                        <link rel="stylesheet" type="text/css" href="/static/styles.css">
                    </head>
                    <body>
                        <h1>Welcome to Drujite API</h1>
                        <p>За южное солнце!</p>
                        <p>${application.environment.config.property("jwt.secret").getString()}</p>
                    </body>
                </html>
                """.trimIndent(),
                ContentType.Text.Html
            )
        }
    }
}
