package com.hamzaerdas.newsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.databinding.NewsListItemBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.utils.dowloadImage
import com.hamzaerdas.newsapp.utils.makePlaceHolder
import com.hamzaerdas.newsapp.utils.recyclerViewDownAnimation
import com.hamzaerdas.newsapp.view.list.ListFragmentDirections
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val list = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    class NewsHolder(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        recyclerViewDownAnimation(holder)

        val news = list[holder.adapterPosition]
        holder.binding.news = news
        holder.binding.listNewsImage.dowloadImage(news.imageUrl, makePlaceHolder(holder.itemView.context))

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(news)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<News>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
