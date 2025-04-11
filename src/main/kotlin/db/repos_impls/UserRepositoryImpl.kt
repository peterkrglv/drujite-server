package db.repos_impls

import UserDAO
import daoToModel
import db.mapping.suspendTransaction
import db.repos.UserRepository
import org.h2.engine.User
import ru.drujite.models.UserModel
import java.util.*

class UserRepositoryImpl : UserRepository {
    override suspend fun getById(id: UUID): UserModel? {
        return suspendTransaction {
            UserDAO.findById(id)?.let { daoToModel(it) }
        }
    }

    override suspend fun getByPhone(phone: String): UserModel? {
        return suspendTransaction {
            UserDAO.find { UserTable.phone eq phone }.firstOrNull()?.let { daoToModel(it) }
        }
    }

    override suspend fun addUser(user: UserModel): UserModel? {
        return suspendTransaction {
            UserDAO.new {
                username = user.username
                phone = user.phone
                password = user.password
                gender = user.gender
            }.let { daoToModel(it) }
        }
    }
}