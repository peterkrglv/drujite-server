package services

import db.repos.UserRepository
import ru.drujite.models.UserModel
import java.util.*

class UserService (
    private val userRepository: UserRepository
) {
    suspend fun findById(id: String): UserModel? {
        return userRepository.getById(UUID.fromString(id))
    }

    suspend fun findByPhone(phone: String): UserModel? {
        return userRepository.getByPhone(phone)
    }

    suspend fun addUser(user: UserModel): UserModel? {
        val foundUser: UserModel? = userRepository.getByPhone(user.phone)
        if (foundUser != null) {
            return null
        }
        return userRepository.addUser(user)
    }
}