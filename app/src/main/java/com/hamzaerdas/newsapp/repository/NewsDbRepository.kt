package com.hamzaerdas.newsapp.repository

import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.service.NewsDao
import javax.inject.Inject

class NewsDbRepository @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend fun getAll() = newsDao.getAll()

    suspend fun add(news: News) = newsDao.add(news)

    suspend fun delete(news: News) = newsDao.delete(news)

    suspend fun isItAdded(id: Int) = newsDao.isItAdded(id)
}