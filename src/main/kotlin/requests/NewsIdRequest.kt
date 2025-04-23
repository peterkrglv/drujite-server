package requests

import kotlinx.serialization.Serializable

@Serializable
data class NewsIdRequest(
    val newsId: Int
)