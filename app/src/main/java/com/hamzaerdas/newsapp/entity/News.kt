package com.hamzaerdas.newsapp.entity

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val publishDate: String,
    val imageUrl: String,
    val body: List<Body>,
    val videoUrl: String,
    val webUrl: String
)