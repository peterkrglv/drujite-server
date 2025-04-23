package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.GoalModel
import requests.AddGoalRequest
import requests.IdRequest
import responses.GoalResponse
import responses.IdResponse
import services.GoalService


fun Route.goalRoute(
    goalService: GoalService
) {
    authenticate {
        get() {
            val request: IdRequest = call.receive<IdRequest>()
            val goal = goalService.getGoal(request.id)
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
            val request = call.receive<IdRequest>()
            val result = goalService.deleteGoal(request.id)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/character-all") {
            val request = call.receive<IdRequest>()
            val goals = goalService.getCharacterGoals(request.id)
            call.respond(
                HttpStatusCode.OK,
                goals.map { it.toResponse() }
            )
        }

        put("/complete") {
            val request = call.receive<IdRequest>()
            val result = goalService.updateGoalStatus(request.id)
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
