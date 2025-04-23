package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.TimeTableModel
import requests.AddTimeTableRequest
import requests.SessionIdRequest
import requests.TimeTableIdRequest
import responses.IdResponse
import responses.TimeTableResponse
import services.TimeTableService

fun Route.timeTableRoute(
    timeTableService: TimeTableService
) {
    authenticate {
        post {
            val request = call.receive<AddTimeTableRequest>()
            val timeTableId = timeTableService.addTimeTable(request.toModel())
            call.respond(HttpStatusCode.Created, IdResponse(timeTableId))
        }

        delete {
            val request = call.receive<TimeTableIdRequest>()
            val result = timeTableService.deleteTimeTable(request.timeTableId)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get {
            val request = call.receive<TimeTableIdRequest>()
            val timeTable = timeTableService.getTimeTable(request.timeTableId)
            if (timeTable != null) {
                call.respond(HttpStatusCode.OK, timeTable.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/session-all") {
            val request = call.receive<SessionIdRequest>()
            val timeTables = timeTableService.getSessionsTimetables(request.sessionId)
            call.respond(
                HttpStatusCode.OK,
                timeTables.map { it.toResponse() }
            )
        }
    }
}

private fun TimeTableModel.toResponse() =
    TimeTableResponse(
        id = this.id,
        sessionId = this.sessionId,
        date = this.date,
    )

private fun AddTimeTableRequest.toModel() =
    TimeTableModel(
        id = 0,
        sessionId = sessionId,
        date = date,
    )
