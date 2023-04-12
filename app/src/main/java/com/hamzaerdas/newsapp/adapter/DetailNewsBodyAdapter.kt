package com.hamzaerdas.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.newsapp.databinding.DetailNewsBodyItemBinding
import com.hamzaerdas.newsapp.entity.Body
import javax.inject.Inject

class DetailNewsBodyAdapter @Inject constructor(): RecyclerView.Adapter<DetailNewsBodyAdapter.DetailBodyHolder>() {

    private val bodyList = ArrayList<Body>()

    class DetailBodyHolder(val binding: DetailNewsBodyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBodyHolder {
        val binding =
            DetailNewsBodyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailBodyHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailBodyHolder, position: Int) {
        val body = bodyList[holder.adapterPosition]
        if(!body.p.isNullOrEmpty()){
            holder.binding.bodyText.text = body.p
        } else {
            holder.binding.bodyText.visibility = View.GONE
        }

        if(!body.h3.isNullOrEmpty()){
            holder.binding.bodyTitle.text = body.h3
        } else {
            holder.binding.bodyTitle.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return bodyList.size
    }

    fun updateList(newBodyList: List<Body>) {
        bodyList.clear()
        bodyList.addAll(newBodyList)
        notifyDataSetChanged()
    }
}