package ru.drujite

import configureDatabases
import db.repos_impls.SessionRepositoryImpl
import db.repos_impls.UserRepositoryImpl
import io.ktor.server.application.*
import routing.configureRouting
import services.JwtService
import services.SessionService
import services.UserService

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserRepositoryImpl()
    val sessionRepository = SessionRepositoryImpl()


    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)
    val sessionService = SessionService(sessionRepository)



    configureSerialization()
    configureRouting(userService, jwtService, sessionService )


    configureSecurity(jwtService)
    configureDatabases()
//    configureMonitoring()
}
