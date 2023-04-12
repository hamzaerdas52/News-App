package com.hamzaerdas.newsapp.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
import com.hamzaerdas.newsapp.entity.News
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsAdapter

    private lateinit var newsList: List<News>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        getAll()
        recyclerViewInitialize()
        observeData()
        refresh()
        menuItemSelect()

        return binding.root
    }

    private fun recyclerViewInitialize() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this@ListFragment.context)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun getAll() {
        viewModel.getAll()
    }

    private fun observeData() {
        viewModel.allNews.observe(viewLifecycleOwner) {
            it?.let {
                newsList = it
                adapter.updateList(newsList)
            }
        }

        viewModel.loadingData.observe(viewLifecycleOwner) {
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

        viewModel.loadingError.observe(viewLifecycleOwner) {
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

    private fun menuItemSelect() {
        binding.includeActionBar.actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.guncel_item -> {
                    categoryFilter("Güncel")
                    true
                }
                R.id.ekonomi_item -> {
                    categoryFilter("Ekonomi")
                    true
                }
                R.id.spor_item -> {
                    categoryFilter("Spor")
                    true
                }
                R.id.politika_item -> {
                    categoryFilter("Politika")
                    true
                }
                R.id.ucuncu_sayfa_item -> {
                    categoryFilter("3. Sayfa")
                    true
                }
                R.id.magazin_item -> {
                    categoryFilter("Magazin")
                    true
                }
                R.id.yerel_item -> {
                    categoryFilter("Yerel")
                    true
                }
                else -> false
            }
        }
    }

    private fun categoryFilter(category: String) {
        val filterList = newsList.filter { it.category == category }.apply { newsList.sortedByDescending { it.publishDate } }
        adapter.updateList(filterList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}