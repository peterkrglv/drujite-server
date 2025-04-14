package requests

import kotlinx.serialization.Serializable

@Serializable
data class SessionRequest(
    //val token??
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String
)