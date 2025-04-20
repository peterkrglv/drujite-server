package db.repos

import models.NewsModel

interface NewsRepository {
    suspend fun add(news: NewsModel): Boolean
    suspend fun get(id: Int): NewsModel
    suspend fun delete(id: Int): Boolean
}