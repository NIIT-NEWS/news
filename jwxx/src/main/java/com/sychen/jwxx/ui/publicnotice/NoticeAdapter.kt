package com.sychen.jwxx.ui.publicnotice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sychen.jwxx.R
import com.sychen.jwxx.network.model.PublicData
import kotlinx.android.synthetic.main.download_item.view.*

class NoticeAdapter : ListAdapter<PublicData, NoticeListViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        return NoticeListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.download_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        val noticeList = getItem(position)
        with(holder.itemView) {
            download_img.load(R.drawable.ic_arrow)
            content.apply {
                text = noticeList.title
                setOnClickListener {
                    Bundle().apply {
                        putString("NOTICE_TITLE", noticeList.title)
                        putString("NOTICE_URL", noticeList.url)
                        Navigation.findNavController(holder.itemView)
                            .navigate(R.id.contentFragment, this)
                    }
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