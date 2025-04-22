package requests

import kotlinx.serialization.Serializable

@Serializable
data class TimeTableIdRequest (
    val timeTableId: Int,
)