package com.hamzaerdas.newsapp.utils

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.R

fun recyclerViewDownAnimation(holder: RecyclerView.ViewHolder) {
    holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_left)
}