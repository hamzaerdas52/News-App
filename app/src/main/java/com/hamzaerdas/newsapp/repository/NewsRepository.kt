package com.hamzaerdas.newsapp.repository

import com.hamzaerdas.newsapp.service.NewsAPIService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsAPIService: NewsAPIService) {
    fun getAll() = newsAPIService.getAll()
}