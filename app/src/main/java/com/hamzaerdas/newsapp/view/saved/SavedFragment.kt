package com.hamzaerdas.newsapp.view.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DbViewModel by viewModels()
    @Inject lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        binding.includeToolbar.mainActionString.text = "Kaydedilen Haberler"

        recyclerViewInitialize()
        observeData()
        adapterItemClick()

        return binding.root
    }

    private fun recyclerViewInitialize() {
        binding.savedNewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.savedNewsRecyclerView.adapter = adapter
    }

    private fun observeData(){
        viewModel.getAll()
        viewModel.savedNewsList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun adapterItemClick(){
        adapter.onItemClick = {
            val action = SavedFragmentDirections.actionSavedFragmentToDetailFragment(arrayOf(it))
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

}