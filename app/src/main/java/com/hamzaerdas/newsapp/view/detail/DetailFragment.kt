package com.hamzaerdas.newsapp.view.detail

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.DetailNewsBodyAdapter
import com.hamzaerdas.newsapp.databinding.FragmentDetailBinding
import com.hamzaerdas.newsapp.utils.detailFragmentAnimation

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoUrl: Uri
    private val adapter = DetailNewsBodyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFragmentAnimation(binding)
        getNavArguments()
        bodyRecyclerViewInitialize()
        videoOperations()
        goBack()
    }

    private fun getNavArguments() {
        arguments.let { it ->
            val news = DetailFragmentArgs.fromBundle(it!!).news
            news.forEach { _it ->
                binding.detailCategory?.let { it.text = _it.category}
                binding.detailTitle?.let { it.text = _it.title }
                binding.detailTime?.let { it.text = _it.publishDate.substring(0, 16) }
                videoUrl = Uri.parse(_it.videoUrl)
                adapter.updateList(_it.body)
            }
        }
    }

    private fun bodyRecyclerViewInitialize(){
        binding.detailNewsBodyRecyclerView?.let {
            it.layoutManager = LinearLayoutManager(this@DetailFragment.context)
            it.adapter = adapter
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
        videoView.start()
        videoView.pause()

        val currentPosition = videoView.currentPosition
        videoView.seekTo(currentPosition)
    }

    private fun goBack(){
        binding.include?.let { it.goBackIcon.setOnClickListener { findNavController().popBackStack() } }
    }
}