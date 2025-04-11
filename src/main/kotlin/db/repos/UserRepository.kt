package db.repos

import ru.drujite.models.UserModel
import java.util.*

interface UserRepository {
    suspend fun getById(id: UUID): UserModel?
    suspend fun getByPhone(phone: String): UserModel?
    suspend fun addUser(user: UserModel): UserModel?
}