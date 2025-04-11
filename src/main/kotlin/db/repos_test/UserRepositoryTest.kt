package ru.drujite.repos

import db.repos.UserRepository
import ru.drujite.models.UserModel
import java.util.*

class UserRepositoryTest: UserRepository {
    private val users = mutableListOf<UserModel>()

    override suspend fun getById(id: UUID): UserModel? = users.firstOrNull { it.id == id }
    override suspend fun getByPhone(phone: String): UserModel? {
        return users.firstOrNull { it.phone == phone }
    }

    override suspend fun addUser(user: UserModel): UserModel? {
        users.add(user)
        return user
    }
}