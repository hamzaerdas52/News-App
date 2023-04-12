package com.hamzaerdas.newsapp.view.detail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.newsapp.adapter.DetailNewsBodyAdapter
import com.hamzaerdas.newsapp.databinding.ActivityDetailBinding
import com.hamzaerdas.newsapp.entity.News
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var videoUrl: Uri

    @Inject
    lateinit var bodyAdapter: DetailNewsBodyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bodyRecyclerViewInitialize()
        getIntentData()
        videoOperations()
        goBack()
    }


    private fun getIntentData(){
        val intent = intent
        val news = intent.getParcelableArrayListExtra<News>("news")
        news?.forEach { _it ->
            binding.detailCategory?.let { it.text = _it.category}
            binding.detailTitle?.let { it.text = _it.title }
            binding.detailTime?.let { it.text = _it.publishDate.substring(0, 16) }
            videoUrl = Uri.parse(_it.videoUrl)
            bodyAdapter.updateList(_it.body)
        }

    }

    private fun bodyRecyclerViewInitialize(){
        binding.detailNewsBodyRecyclerView?.let {
            it.layoutManager = LinearLayoutManager(this@DetailActivity)
            it.adapter = bodyAdapter
        }
    }

    private fun videoOperations(){
        val videoView = binding.detailVideoView
        val mediaController = MediaController(this@DetailActivity)
        mediaController.setAnchorView(videoView)

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoUrl)
        videoView.requestFocus()
        videoView.pause()

        val currentPosition = videoView.currentPosition
        videoView.seekTo(currentPosition)
    }

    private fun goBack(){
        binding.include?.let { it.goBackIcon.setOnClickListener { finish() } }
    }
}