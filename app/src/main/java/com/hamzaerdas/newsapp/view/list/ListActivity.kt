package com.hamzaerdas.newsapp.view.list

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.ActivityListBinding
import com.hamzaerdas.newsapp.entity.News
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel: ListViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsAdapter
    private lateinit var newsList: List<News>
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAll()
        recyclerViewInitialize()
        observeData()
        refresh()
    }

    private fun recyclerViewInitialize() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this@ListActivity)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun getAll() {
        viewModel.getAll()
    }

    private fun observeData() {
        viewModel.allNews.observe(this@ListActivity) {
            it?.let {
                newsList = it
                adapter.updateList(newsList)
            }
        }

        viewModel.loadingData.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.includeErrorView.errorView.visibility = View.GONE
                } else {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                }
            }
        }

        viewModel.loadingError.observe(this@ListActivity) {
            it?.let {
                if (it) {
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeErrorView.errorView.visibility = View.VISIBLE
                } else {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeErrorView.errorView.visibility = View.GONE
                }
            }
        }
    }

    private fun refresh() {
        binding.listSwipeRefresh.setOnRefreshListener {
            binding.includeLoadingView.loadingView.visibility = View.VISIBLE
            binding.newsRecyclerView.visibility = View.GONE
            getAll()
            binding.listSwipeRefresh.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.search)
        searchView = menuItem.actionView?.findViewById(R.id.search)!!
        searchView.queryHint = "Haber ara"
        searchView()
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterData(p0)
                return true
            }
        })

        binding.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    searchView.clearFocus()
                    hideKeyboard()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                searchView.requestFocus()
            }
            R.id.guncel_item -> {
                categoryFilter("Güncel")
            }
            R.id.ekonomi_item -> {
                categoryFilter("Ekonomi")

            }
            R.id.spor_item -> {
                categoryFilter("Spor")

            }
            R.id.politika_item -> {
                categoryFilter("Politika")

            }
            R.id.ucuncu_sayfa_item -> {
                categoryFilter("3. Sayfa")

            }
            R.id.magazin_item -> {
                categoryFilter("Magazin")

            }
            R.id.yerel_item -> {
                categoryFilter("Yerel")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun categoryFilter(category: String) {
        val filteredList: List<News> = newsList.filter { it.category == category }
            .apply { newsList.sortedByDescending { it.publishDate } }
        adapter.updateList(filteredList)
    }

    private fun filterData(p0: String?) {
        p0?.let {
            val filteredList = ArrayList<News>()
            for (item in newsList) {
                if (item.title.lowercase(Locale.ROOT).contains(p0)) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                adapter.updateList(listOf())
                Toast.makeText(this@ListActivity, "No data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateList(filteredList)
            }
        }

    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}