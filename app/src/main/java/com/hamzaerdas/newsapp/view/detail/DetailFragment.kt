package com.hamzaerdas.newsapp.view.detail

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.adapter.DetailNewsBodyAdapter
import com.hamzaerdas.newsapp.databinding.FragmentDetailBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.view.saved.DbViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoUrl: Uri
    @Inject
    lateinit var adapter: DetailNewsBodyAdapter
    private val viewModel: DbViewModel by viewModels()
    private var id: Int? = 0
    private lateinit var savedButton: ImageView
    private var isItAdded: Boolean = false
    private var webUrl:String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        savedButton = binding.includeDetailToolBar?.addIcon!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArguments()
        bodyRecyclerViewInitialize()
        videoOperations()
        goBack()
        webButtonClick()

    }

    private fun getNavArguments() {
        arguments.let {
            val news = DetailFragmentArgs.fromBundle(it!!).news
            news.forEach { _it ->
                id = _it.id
                observeData()
                isItSaved(_it)
                binding.detailCategory?.let { it.text = _it.category}
                binding.detailTitle?.let { it.text = _it.title }
                binding.detailTime?.let { it.text = _it.publishDate!!.substring(0, 16) }
                videoUrl = Uri.parse(_it.videoUrl)
                webUrl = _it.webUrl
                adapter.updateList(_it.body!!)
            }
        }
    }

    private fun bodyRecyclerViewInitialize(){
        binding.detailNewsBodyRecyclerView?.let {
            it.layoutManager = LinearLayoutManager(this@DetailFragment.context)
            it.adapter = adapter
        }
    }

    private fun observeData(){
        viewModel.isItAdded(id!!)
        viewModel.isAdded.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    savedButton.setImageResource(R.drawable.bookmark_icon)
                    isItAdded = true
                }
            }
        }
    }

    private fun isItSaved(news: News){
        savedButton.setOnClickListener {
            isItAdded = if (isItAdded) {
                savedButton.setImageResource(R.drawable.bookmark_border_icon)
                viewModel.delete(news)
                false
            } else {
                savedButton.setImageResource(R.drawable.bookmark_icon)
                viewModel.add(news)
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun videoOperations(){
        val videoView = binding.detailVideoView
        val mediaController = MediaController(this@DetailFragment.context)
        mediaController.setAnchorView(videoView)

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoUrl)
        videoView.requestFocus()
        videoView.pause()

    }

    private fun goBack(){
        binding.includeDetailToolBar?.goBackIcon?.setOnClickListener { findNavController().popBackStack() }
    }

    private fun webButtonClick(){
        binding.webButton?.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToWebViewFragment(webUrl!!)
            Navigation.findNavController(requireView()).navigate(action)
        }
    }
}