package com.sychen.home.ui.home

import android.content.Intent
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
import com.sychen.basic.util.SharedPreferencesUtil.sharedPreferencesLoad
import com.sychen.basic.util.dataStoreRead
import com.sychen.home.R
import com.sychen.home.activity.NewsActivity
import com.sychen.home.network.model.New
import kotlinx.android.synthetic.main.news_item.view.*


class NewsListRecyclerAdapter : ListAdapter<New.Data, NewsListViewHolder>(DIFFCALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val newsList = getItem(position)
        with(holder.itemView) {
            cardView.cardElevation = sharedPreferencesLoad<Int>("CARD_RADIUS").toString().toFloat()
            cardView.radius = sharedPreferencesLoad<Int>("CARD_ELEVATION").toString().toFloat()
            new_title.text = newsList.newTitle
            new_author.text = newsList.author
            new_date.text = newsList.date
            new_img.load(newsList.newTitleImgUrl) {
                // 淡入淡出
                crossfade(true)
                placeholder(R.drawable.ic_baseline_photo_24)
                error(R.drawable.ic_baseline_broken_image_24)
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.setClass(holder.itemView.context,NewsActivity::class.java)
            intent.putExtra("NEWS",Gson().toJson(newsList))
            holder.itemView.context.startActivity(intent)
        }

    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<New.Data>() {
        override fun areItemsTheSame(oldItem: New.Data, newItem: New.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: New.Data, newItem: New.Data): Boolean {
            return oldItem == newItem
        }
    }

}

class NewsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)