package com.sychen.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.sychen.home.R
import com.sychen.home.network.model.NewsC
import kotlinx.android.synthetic.main.news_item.view.*


class NewsListRecyclerAdapter : ListAdapter<NewsC.Data, NewsListViewHolder>(DIFFCALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val newsList = getItem(position)
        with(holder.itemView) {
            new_title.text = newsList.newTitle
            new_create.text = newsList.creator
            new_img.load(newsList.newTitleImgUrl) {
                // 淡入淡出
                crossfade(true)
                placeholder(R.drawable.ic_baseline_photo_24)
                error(R.drawable.ic_baseline_broken_image_24)
            }
        }
        holder.itemView.setOnClickListener {
            Bundle().apply {
                putString("NEWS",Gson().toJson(newsList))
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_newDetailsFragment, this)
            }
        }

    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<NewsC.Data>() {
        override fun areItemsTheSame(oldItem: NewsC.Data, newItem: NewsC.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsC.Data, newItem: NewsC.Data): Boolean {
            return oldItem == newItem
        }
    }

}

class NewsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)