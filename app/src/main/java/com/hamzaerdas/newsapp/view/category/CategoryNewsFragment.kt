package com.hamzaerdas.newsapp.view.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentCategoryNewsBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.utils.Search
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryNewsFragment : Fragment() {

    private var _binding: FragmentCategoryNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsAdapter
    @Inject lateinit var search: Search
    private lateinit var category: String
    private lateinit var newsList: List<News>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryNewsBinding.inflate(inflater, container, false)

        getNavArguments()
        recyclerViewInitialize()
        observeData()
        recyclerItemClick()
        goBack()

        return binding.root
    }

    private fun getNavArguments() {
        arguments.let {
            category = CategoryNewsFragmentArgs.fromBundle(it!!).category
        }
        binding.actionBar.goBackIcon.visibility = View.VISIBLE
        binding.actionBar.mainActionString.text = "$category"
    }

    private fun recyclerViewInitialize() {
        binding.categoryNewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryNewsRecyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.getToCategory(category)
        viewModel.newsByCategory.observe(viewLifecycleOwner) {
            newsList = it
            adapter.updateList(newsList)
            search()
        }

        viewModel.loadingData.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.includeLoadingView.loadingView.visibility = View.VISIBLE
                    binding.includeSearchView.searchView.visibility = View.GONE
                    binding.categoryNewsRecyclerView.visibility = View.GONE
                } else {
                    binding.includeLoadingView.loadingView.visibility = View.GONE
                    binding.includeSearchView.searchView.visibility = View.VISIBLE
                    binding.categoryNewsRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun recyclerItemClick() {
        adapter.onItemClick = {
            val action = CategoryNewsFragmentDirections.actionCategoryNewsFragmentToDetailFragment(arrayOf(it))
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun goBack(){
        binding.actionBar.goBackIcon.setOnClickListener { findNavController().popBackStack() }
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