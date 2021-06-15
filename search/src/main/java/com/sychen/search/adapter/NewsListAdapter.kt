package com.sychen.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sychen.search.R
import com.sychen.search.network.model.News
import kotlinx.android.synthetic.main.search_new_item.view.*

class NewsListAdapter :
    ListAdapter<News,NewsListAdapter.MyViewHolder>(DIFFCALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_new_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        with(holder.itemView) {
            searchnewTitle.text = news.newTitle
            searchnewAuthor.text = news.author
            searchnewDate.text = news.date
            imageView.load(news.newTitleImgUrl) {
                // 淡入淡出
                crossfade(true)
                transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
                placeholder(R.drawable.ic_baseline_photo_24)
                error(R.drawable.ic_baseline_broken_image_24)
            }
        }
    }
    object DIFFCALLBACK : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
