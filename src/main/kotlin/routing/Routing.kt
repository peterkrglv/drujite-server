package routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.drujite.routing.authRoute
import services.JwtService
import services.UserService

fun Application.configureRouting(
    userService: UserService,
    jwtService: JwtService,
) {
    routing {
        static("/static") {
            resources("static")
        }

        route("/api/v1/user") {
            userRoute(userService)
        }

        route("/api/v1/auth") {
            authRoute(jwtService)
        }

        route("/api/v1/signup") {
            signupRoute(jwtService, userService)
        }
        get("/api/v1/") {
            println(application.environment.config.property("jwt.secret").getString())
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