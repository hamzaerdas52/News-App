package com.hamzaerdas.newsapp.service

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hamzaerdas.newsapp.entity.News

@Dao
interface NewsDao {

    @Insert
    suspend fun add(news: News)

    @Delete
    suspend fun delete(news: News)

    @Query("SELECT * FROM News")
    suspend fun getAll() : List<News>

    @Query("SELECT COUNT(*) FROM News WHERE id = :id")
    suspend fun isItAdded(id: Int) : Int
}