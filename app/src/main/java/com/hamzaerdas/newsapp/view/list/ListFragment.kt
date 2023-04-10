package com.hamzaerdas.newsapp.view.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
import com.hamzaerdas.newsapp.entity.News
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        viewModelInitialize()
        recyclerViewInitialize()
        getAll()
        observeData()
        refresh()
        menuItemSelect()

        return binding.root
    }

    private fun viewModelInitialize() {
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
    }

    private fun recyclerViewInitialize() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this@ListFragment.context)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun getAll() {
        viewModel.getAll()
    }

    private fun menuSelect(category: String) {
        viewModel.getToCategory(category)
        observeData()
    }

    private fun observeData() {
        viewModel.allNews.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateList(it)
            }
        }

        viewModel.newsByCategory.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateList(it)
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
                    menuSelect("Güncel")
                    true
                }
                R.id.ekonomi_item -> {
                    menuSelect("Ekonomi")
                    true
                }
                R.id.spor_item -> {
                    menuSelect("Spor")
                    true
                }
                R.id.politika_item -> {
                    menuSelect("Politika")
                    true
                }
                R.id.ucuncu_sayfa_item -> {
                    menuSelect("3. Sayfa")
                    true
                }
                R.id.magazin_item -> {
                    menuSelect("Magazin")
                    true
                }
                R.id.yerel_item -> {
                    menuSelect("Yerel")
                    true
                }
                else -> false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}