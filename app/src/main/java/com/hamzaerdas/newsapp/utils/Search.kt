package com.hamzaerdas.newsapp.utils

import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentCategoryNewsBinding
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
import com.hamzaerdas.newsapp.entity.News
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class Search @Inject constructor() {

    fun searchData(searchView: SearchView, newsList: List<News>, adapter: NewsAdapter) {
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterData(p0, newsList, adapter)
                return true
            }
        })
    }

    private fun filterData(
        p0: String?,
        newsList: List<News>,
        adapter: NewsAdapter
    ) {
        p0?.let {
            val filteredList = ArrayList<News>()
            for (item in newsList) {
                if (item.title!!.lowercase(Locale.ROOT).contains(p0)) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                adapter.updateList(listOf())
            } else {
                adapter.updateList(filteredList)
            }
        }
    }

    fun clearFocus(binding: FragmentListBinding){
        binding.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    binding.includeSearchView.searchView.clearFocus()
                }
            }
        })
    }

    fun clearFocus(binding: FragmentCategoryNewsBinding){
        binding.categoryNewsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    binding.includeSearchView.searchView.clearFocus()
                }
            }
        })
    }
}