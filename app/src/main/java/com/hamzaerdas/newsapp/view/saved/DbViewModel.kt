package com.hamzaerdas.newsapp.view.saved

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.repository.NewsDbRepository
import com.hamzaerdas.newsapp.service.NewsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(
    private val newsDbRepository: NewsDbRepository,
    application: Application
) : AndroidViewModel(application) {

    var savedNewsList = MutableLiveData<List<News>>()
    var isAdded = MutableLiveData<Boolean>()

    fun add(news: News) = viewModelScope.launch {
        newsDbRepository.add(news)
        Toast.makeText(getApplication(), "Kaydedilenlere eklendi", Toast.LENGTH_SHORT).show()
    }

    fun delete(news: News) = viewModelScope.launch {
        newsDbRepository.delete(news)
        Toast.makeText(getApplication(), "Kaydedilenlerden kaldırıldı", Toast.LENGTH_SHORT).show()
    }

    fun getAll() = viewModelScope.launch {
        savedNewsList.value = newsDbRepository.getAll()
    }

    fun isItAdded(id: Int) = viewModelScope.launch {
        val added = newsDbRepository.isItAdded(id)
        isAdded.value = added == 1
    }


}