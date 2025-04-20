package models

data class NewsModel (
    val id: Int,
    val sessionId: Int,
    val title: String,
    val content: String,
    val time: String,
    val imageUrl: String? = null,
)