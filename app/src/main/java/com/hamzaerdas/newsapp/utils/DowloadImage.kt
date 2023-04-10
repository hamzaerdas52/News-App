package com.hamzaerdas.newsapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.hamzaerdas.newsapp.R
import com.squareup.picasso.Picasso

fun ImageView.dowloadImage(url: String?, placeholder: CircularProgressDrawable) {

    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .error(R.drawable.error_icon)
        .into(this);
}

fun makePlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 35f
        start()
    }
}

fun dowloadImage(view: ImageView, url: String?){
    view.dowloadImage(url, makePlaceHolder(view.context))
}