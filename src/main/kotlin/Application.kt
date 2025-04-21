package ru.drujite

import configureDatabases
import db.repos_impls.*
import io.ktor.server.application.*
import routing.configureRouting
import services.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserRepositoryImpl()
    val sessionRepository = SessionRepositoryImpl()
    val usersSessionRepository = UsersSessionsRepositoryImpl()
    val characterRepository = CharacterRepositoryImpl()
    val goalRepository = GoalRepositoryImpl()


    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)
    val sessionService = SessionService(sessionRepository)
    val usersSessionService = UsersSessionsService(usersSessionRepository, characterRepository)
    val characterService = CharacterService(characterRepository)
    val goalService = GoalService(goalRepository)


    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(userService, jwtService, sessionService, usersSessionService, characterService, goalService)
    configureDatabases()
    configureMonitoring()
}
