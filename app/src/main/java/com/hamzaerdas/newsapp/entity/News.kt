package com.hamzaerdas.newsapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Int,
    val title: String,
    val category: String,
    val publishDate: String,
    val imageUrl: String,
    val body: List<Body>,
    val videoUrl: String,
    val webUrl: String
) : Parcelable