package com.hamzaerdas.newsapp.utils

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.R
import com.hamzaerdas.newsapp.databinding.FragmentDetailBinding

fun detailFragmentAnimation(binding: FragmentDetailBinding){
    binding.root.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_right)
}

fun recyclerViewDownAnimation(holder: RecyclerView.ViewHolder) {
    holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_left)
}