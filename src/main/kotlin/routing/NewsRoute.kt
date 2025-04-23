package routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.NewsModel
import requests.AddNewsRequest
import requests.NewsIdRequest
import requests.SessionIdRequest
import responses.IdResponse
import responses.NewsResponse
import services.NewsService

fun Route.newsRoute(
    newsService: NewsService
) {
    authenticate {
        post() {
            val request = call.receive<AddNewsRequest>()
            val news = request.toModel()
            val newsId = newsService.add(news)
            call.application.environment.log.info("News $newsId added")
            call.respond(HttpStatusCode.OK, IdResponse(newsId))

        }

        delete() {
            val request = call.receive<NewsIdRequest>()
            val result = newsService.delete(request.newsId)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get() {
            val request = call.receive<NewsIdRequest>()
            val news = newsService.get(request.newsId)
            if (news != null) {
                call.respond(HttpStatusCode.OK, news.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/session") {
            val request = call.receive<SessionIdRequest>()
            val newsList = newsService.getSessionsNews(request.sessionId)
            if (newsList.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, newsList.map { it.toResponse() })
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

private fun NewsModel.toResponse() =
    NewsResponse(
        id = id,
        sessionId = sessionId,
        title = title,
        content = content,
        dateTime = dateTime,
        imageUrl = imageUrl
    )

private fun AddNewsRequest.toModel() =
    NewsModel(
        id = 0,
        sessionId = sessionId,
        title = title,
        content = content,
        dateTime = "",
        imageUrl = imageUrl
    )