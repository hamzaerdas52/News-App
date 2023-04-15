package com.hamzaerdas.newsapp.service

import com.hamzaerdas.newsapp.entity.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    // http://app.haberler.com/services/haberlercom/2.11/service.asmx/
    // haberler?category=manset&count=35&offset=0&deviceType=1&userId=61ed99e0c09a8664

    @GET("haberler?category=manset&count=85&offset=1&deviceType=1&userId=61ed99e0c09a8664")
    fun getAll(): Single<NewsResponse>

    @GET("haberler?count=85&offset=1&deviceType=1&userId=61ed99e0c09a8664")
    fun getToCategory(
        @Query("category") category: String
    ): Single<NewsResponse>
}