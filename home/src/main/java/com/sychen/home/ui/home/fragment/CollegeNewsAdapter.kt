package com.sychen.home.ui.home.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.sychen.home.R
import com.sychen.home.activity.NewsActivity
import com.sychen.home.network.model.NiitNews

class CollegeNewsAdapter : PagingDataAdapter<NiitNews.News, CollegeNewsAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<NiitNews.News>() {
            override fun areItemsTheSame(oldItem: NiitNews.News, newItem: NiitNews.News): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NiitNews.News,
                newItem: NiitNews.News
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleImg: ImageView = itemView.findViewById(R.id.new_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.college_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        if (newsItem != null) {
            with(holder) {
                titleImg.load(newsItem.newTitleImgUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
                    placeholder(R.drawable.ic_baseline_photo_24)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
            }
            holder.itemView.setOnClickListener {
                val intent = Intent()
                intent.setClass(holder.itemView.context, NewsActivity::class.java)
                intent.putExtra("NEWS", Gson().toJson(newsItem))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

}
