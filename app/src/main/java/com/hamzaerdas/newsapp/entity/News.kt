package com.hamzaerdas.newsapp.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "News")
data class News(
    @PrimaryKey
    var id: Int? = 0,

    @ColumnInfo(name = "title")
    var title: String? = "",

    @ColumnInfo(name = "category")
    var category: String? = "",

    @ColumnInfo(name = "publishDate")
    var publishDate: String? = "",

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = "",

    @ColumnInfo(name = "body")
    @Ignore var body: ArrayList<Body>? = ArrayList<Body>(),

    @ColumnInfo(name = "videoUrl")
    var videoUrl: String? = "",

    @ColumnInfo(name = "webUrl")
    var webUrl: String? = "",

    @ColumnInfo(name = "saved")
    var save: Boolean? = false
) : Parcelable
