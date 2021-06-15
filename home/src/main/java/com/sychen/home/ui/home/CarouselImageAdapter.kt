package com.sychen.home.ui.home

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sychen.home.network.model.Banner
import com.youth.banner.adapter.BannerAdapter

class CarouselImageAdapter(bannerList: List<Banner.Data>) :
    BannerAdapter<Banner.Data, CarouselImageAdapter.BannerViewHolder>(bannerList) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder? {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, banner: Banner.Data, position: Int, size: Int) {
        //holder.imageView.setImageResource(data.imageRes);
        holder.imageView.let { Glide.with(it.context).load(banner.imgUrl).into(holder.imageView) }
        holder.itemView.setOnClickListener { v -> //点击事件

        }
    }

    class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

}