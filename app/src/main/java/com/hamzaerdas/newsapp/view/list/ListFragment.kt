package com.hamzaerdas.newsapp.view.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.NewsAdapter
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelInitialize()
        recyclerViewInitialize()
        getAll()
        observeData()
    }

    private fun viewModelInitialize() {
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
    }

    private fun recyclerViewInitialize(){
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this@ListFragment.context)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun getAll(){
        viewModel.getAllNews()
    }

    private fun observeData(){

        viewModel.allNews.observe(viewLifecycleOwner){
            it?.let {
                adapter.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}