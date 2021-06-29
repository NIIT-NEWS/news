package com.sychen.jwxx.ui.publicnotice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.sychen.jwxx.R
import com.sychen.jwxx.network.model.PublicData
import com.sychen.jwxx.ui.content.ContentActivity
import kotlinx.android.synthetic.main.download_item.view.*

class NoticeAdapter : ListAdapter<PublicData, NoticeListViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        return NoticeListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.download_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        val notice = getItem(position)
        with(holder.itemView) {
            download_img.load(R.drawable.ic_arrow)
            content.apply {
                text = notice.title
                setOnClickListener {
                    val intent = Intent()
                    intent.putExtra("NOTICE_INFO",Gson().toJson(notice))
                    intent.setClass(holder.itemView.context,ContentActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<PublicData>() {
        override fun areItemsTheSame(oldItem: PublicData, newItem: PublicData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PublicData, newItem: PublicData): Boolean {
            return oldItem == newItem
        }
    }
}

class NoticeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)