package com.hamzaerdas.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamzaerdas.newsapp.databinding.ActivityMainBinding
import com.hamzaerdas.newsapp.databinding.FragmentListBinding
import com.hamzaerdas.newsapp.view.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}