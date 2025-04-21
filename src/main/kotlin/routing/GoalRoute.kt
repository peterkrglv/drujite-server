package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.GoalModel
import requests.AddGoalRequest
import requests.CharacterRequest
import requests.GoalRequest
import responces.GoalResponse
import responces.IdResponse
import services.GoalService
import services.JwtService


fun Route.goalRoute(
    goalService: GoalService
) {
    authenticate {
        get() {
            val request: GoalRequest = call.receive<GoalRequest>()
            val goal = goalService.getGoal(request.goalId)
            if (goal != null) {
                call.respond(goal.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post() {
            val request = call.receive<AddGoalRequest>()
            val goalId = goalService.addGoal(request)
            call.respond(HttpStatusCode.Created, IdResponse(goalId))
        }

        delete() {
            val request = call.receive<GoalRequest>()
            val result = goalService.deleteGoal(request.goalId)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/character-all") {
            val request = call.receive<CharacterRequest>()
            val goals = goalService.getCharacterGoals(request.characterId)
            call.respond(
                HttpStatusCode.OK,
                goals.map { it.toResponse() }
            )
        }

        put("/complete") {
            val request = call.receive<GoalRequest>()
            val result = goalService.updateGoalStatus(request.goalId)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

private fun GoalModel.toResponse() = GoalResponse(
    id = this.id,
    characterId = this.characterId,
    name = this.name,
    isCompleted = this.isCompleted,
)
