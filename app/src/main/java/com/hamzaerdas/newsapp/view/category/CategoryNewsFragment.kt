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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryNewsFragment : Fragment() {

    private var _binding: FragmentCategoryNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsAdapter
    private lateinit var category: String

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
            println(category)
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
            adapter.updateList(it)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}