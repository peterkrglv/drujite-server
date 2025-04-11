package ru.drujite

import configureDatabases
import db.repos_impls.UserRepositoryImpl
import io.ktor.server.application.*
import ru.drujite.repos.UserRepositoryTest
import routing.configureRouting
import services.JwtService
import services.UserService

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserRepositoryImpl()
    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)



    configureSerialization()
    configureRouting(userService, jwtService)


    configureSecurity(jwtService)
    configureDatabases()
//    configureMonitoring()
//    configureDatabases()
}
