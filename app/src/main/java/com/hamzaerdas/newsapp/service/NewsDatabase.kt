package com.hamzaerdas.newsapp.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamzaerdas.newsapp.entity.Body
import com.hamzaerdas.newsapp.entity.News

@Database(entities = [News::class, Body::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun getNewsDao() : NewsDao
}