package com.hamzaerdas.newsapp.view.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.entity.NewsResponse
import com.hamzaerdas.newsapp.repository.NewsAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: NewsAPIRepository) : ViewModel() {

    val allNews = MutableLiveData<List<News>>()
    val loadingData = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun getAll() {

        loadingData.value = true

        disposable.add(
            repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        allNews.value = t.news.sortedByDescending { it.publishDate }
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