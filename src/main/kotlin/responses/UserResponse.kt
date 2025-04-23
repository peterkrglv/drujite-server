package ru.drujite.responces

import kotlinx.serialization.Serializable
import java.util.*
import ru.drujite.util.UUIDSerializer

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String
)