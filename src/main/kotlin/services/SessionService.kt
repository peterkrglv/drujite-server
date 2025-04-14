package services

import db.repos.SessionRepository
import models.SessionModel
import requests.SessionRequest
import requests.GetSession
import java.util.*

class SessionService(
    private val sessionRepository: SessionRepository,
) {

    suspend fun getSessionById(request: GetSession) = sessionRepository.getById(request.id)

    suspend fun addSession(
        request: SessionRequest
    ) = sessionRepository.addSession(
        SessionModel(
            id = 0,
            name = request.name,
            description = request.description,
            startDate = request.startDate,
            endDate = request.endDate,
            imageUrl = request.imageUrl,
        )
    )

    suspend fun deleteSession(id: Int) = sessionRepository.deleteSession(id)

    suspend fun getSessionsByUserId(userId: String) =
        sessionRepository.getSessionsByUserId(
            UUID.fromString(userId),
        )
}