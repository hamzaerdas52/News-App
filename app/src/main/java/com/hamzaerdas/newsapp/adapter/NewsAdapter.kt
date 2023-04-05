package com.hamzaerdas.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.databinding.RecyclerListItemBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.utils.dowloadImage
import com.hamzaerdas.newsapp.utils.makePlaceHolder

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val newsList = ArrayList<News>()

    class NewsHolder(val binding: RecyclerListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = RecyclerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val news = newsList[holder.adapterPosition]
        holder.binding.titleText.text = news.title
        holder.binding.categoryText.text = news.category
        holder.binding.listNewsImage.dowloadImage(news.imageUrl, makePlaceHolder(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateList(newNewsList: List<News>){
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }

}
