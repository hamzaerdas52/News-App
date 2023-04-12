package com.hamzaerdas.newsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.databinding.RecyclerListItemBinding
import com.hamzaerdas.newsapp.entity.News
import com.hamzaerdas.newsapp.utils.dowloadImage
import com.hamzaerdas.newsapp.utils.makePlaceHolder
import com.hamzaerdas.newsapp.utils.recyclerViewDownAnimation
import com.hamzaerdas.newsapp.view.detail.DetailActivity
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val newsList = ArrayList<News>()

    class NewsHolder(val binding: RecyclerListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = RecyclerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        recyclerViewDownAnimation(holder)

        val news = newsList[holder.adapterPosition]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putParcelableArrayListExtra("news", arrayListOf(news))
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.news = news
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
