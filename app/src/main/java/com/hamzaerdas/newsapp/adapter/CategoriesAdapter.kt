package com.hamzaerdas.newsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.databinding.CategoriesListItemBinding
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(): RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {

    private val list = ArrayList<String>()
    var onItemClick: ((String) -> Unit)? = null

    class CategoriesHolder(val binding: CategoriesListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val binding = CategoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val category = list[holder.adapterPosition]
        holder.binding.categoryListText.text = category
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<String>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}