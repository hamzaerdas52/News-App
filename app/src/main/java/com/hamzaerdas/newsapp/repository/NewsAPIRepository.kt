package com.hamzaerdas.newsapp.repository

import com.hamzaerdas.newsapp.service.NewsAPIService
import javax.inject.Inject

class NewsAPIRepository @Inject constructor(
    private val newsAPIService: NewsAPIService
) {
    fun getAll() = newsAPIService.getAll()
    fun getToCategory(category: String) = newsAPIService.getToCategory(category)
}