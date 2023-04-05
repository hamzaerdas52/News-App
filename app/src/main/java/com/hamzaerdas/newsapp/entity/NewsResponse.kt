package com.hamzaerdas.newsapp.entity

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("news")
    val news: List<News>
)