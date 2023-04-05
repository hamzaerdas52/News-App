package com.hamzaerdas.newsapp.service

import com.hamzaerdas.newsapp.entity.NewsResponse
import com.hamzaerdas.newsapp.utils.Constants.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    fun getAll(): Single<NewsResponse>{
        return api.getAll()
    }
}