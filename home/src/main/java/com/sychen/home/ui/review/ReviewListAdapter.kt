package com.sychen.home.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.sychen.home.R
import com.sychen.home.network.model.Review
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewListAdapter : ListAdapter<Review.Data, ReviewListViewHolder>(DIFFCALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        return ReviewListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        val newsList = getItem(position)
        with(holder.itemView) {
            user_avatar.load(newsList.avatar){
                // 淡入淡出
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_baseline_photo_24)
                error(R.drawable.ic_baseline_broken_image_24)
            }
            user_content.text = newsList.content
            user_name.text = newsList.nickname
            content_date.text = newsList.date
        }

    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<Review.Data>() {
        override fun areItemsTheSame(oldItem: Review.Data, newItem: Review.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review.Data, newItem: Review.Data): Boolean {
            return oldItem == newItem
        }
    }

}

class ReviewListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)