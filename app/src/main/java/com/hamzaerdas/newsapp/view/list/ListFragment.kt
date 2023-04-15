package com.hamzaerdas.newsapp.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.utils.Search
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
    @Inject lateinit var search: Search

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.includeToolBar.mainActionString.text = "Manşet Haberleri"

        recyclerViewInitialize()
        observeData()
        adapterItemClick()
        refresh()

        return binding.root
    }

    private fun recyclerViewInitialize() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this@ListFragment.context)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.getAll()
        viewModel.allNews.observe(viewLifecycleOwner) {
            it?.let {
                newsList = it
                adapter.updateList(newsList)
                search()
            }
        }

        viewModel.loadingData.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeErrorView.errorView.visibility = View.GONE
                } else {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                }
            }
        }

        viewModel.loadingError.observe(viewLifecycleOwner){
            it?.let {
                if(it){
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                } else {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.VISIBLE
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeErrorView.errorView.visibility = View.GONE
                }
            }
        }
    }

    private fun adapterItemClick() {
        adapter.onItemClick = {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(arrayOf(it))
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun refresh(){
        binding.listSwipeRefresh.setOnRefreshListener {
            binding.includeLoadingView.loadingView.visibility = View.VISIBLE
            binding.includeErrorView.errorView.visibility = View.GONE
            binding.newsRecyclerView.visibility = View.GONE
            viewModel.getAll()
            binding.listSwipeRefresh.isRefreshing = false
        }
    }

    private fun search(){
        val searchView = binding.includeSearchView.searchView
        search.searchData(searchView, newsList, adapter)
        search.clearFocus(binding)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}