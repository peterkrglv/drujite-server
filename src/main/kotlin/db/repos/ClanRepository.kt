package db.repos

import models.ClanModel

interface ClanRepository {
    suspend fun add(clan: ClanModel): Boolean
    suspend fun get(id: Int): ClanModel
    suspend fun delete(id: Int): Boolean
}