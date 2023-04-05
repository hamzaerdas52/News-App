package com.hamzaerdas.newsapp.view.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.entity.NewsResponse
import com.hamzaerdas.newsapp.service.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    val allNews = MutableLiveData<List<News>>()
    val loadingData = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()

    private val newsService = NewsService()
    private val disposable = CompositeDisposable()

    fun getAllNews(){
        getAll()
    }

    private fun getAll() {

        loadingData.value = true

        disposable.add(
            newsService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        allNews.value = t.news.sortedBy { it.publishDate }
                        loadingData.value = false
                        loadingError.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Hata", "Veri Gelmedi", e)
                        loadingError.value = true
                    }

                })
        )

    }

}