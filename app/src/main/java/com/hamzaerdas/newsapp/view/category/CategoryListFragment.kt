package com.hamzaerdas.newsapp.view.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.CategoriesAdapter
import com.hamzaerdas.newsapp.databinding.FragmentCategoryListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()
    @Inject
    lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)

        categoryList()

        return binding.root
    }

    private fun categoryList() {
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.categoryRecyclerView.adapter = adapter
        adapter.updateList(viewModel.categoryList)
        adapter.onItemClick = {
            val action = CategoryListFragmentDirections.actionCategoryFragmentToCategoryNewsFragment(it)
            Navigation.findNavController(requireView()).navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}