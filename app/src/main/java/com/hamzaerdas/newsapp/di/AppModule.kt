package com.hamzaerdas.newsapp.di

import android.content.Context
import androidx.room.Room
import com.hamzaerdas.newsapp.service.NewsAPIService
import com.hamzaerdas.newsapp.service.NewsDatabase
import com.hamzaerdas.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getNewsAPI(): NewsAPIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "newsdatabase"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideNewsDao(database: NewsDatabase) = database.getNewsDao()

}