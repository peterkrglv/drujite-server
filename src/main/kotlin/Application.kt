package ru.drujite

import configureDatabases
import db.repos_impls.SessionRepositoryImpl
import db.repos_impls.UserRepositoryImpl
import db.repos_impls.UsersSessionsRepositoryImpl
import io.ktor.server.application.*
import routing.configureRouting
import services.JwtService
import services.SessionService
import services.UserService
import services.UsersSessionsService

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserRepositoryImpl()
    val sessionRepository = SessionRepositoryImpl()
    val usersSessionRepository = UsersSessionsRepositoryImpl()


    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)
    val sessionService = SessionService(sessionRepository)
    val usersSessionService = UsersSessionsService(usersSessionRepository)


    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(userService, jwtService, sessionService, usersSessionService)
    configureDatabases()
    configureMonitoring()
}
