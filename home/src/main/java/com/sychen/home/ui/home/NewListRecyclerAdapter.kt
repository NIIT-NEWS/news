package com.sychen.home.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.MyApplication.Companion.showToastShort
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.dataStoreRead
import com.sychen.home.R
import com.sychen.home.activity.NewsActivity
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.Banner
import com.sychen.home.network.model.NiitNews
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.banner.view.*
import kotlinx.android.synthetic.main.news_item.view.*
import com.sychen.home.ui.home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsListRecyclerAdapter(val newsList: List<NiitNews.News>, val bannerList: List<Banner>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val TYPE_HEADER: Int = 0
        private val TYPE_LIST: Int = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_LIST
    }

    override fun getItemCount(): Int {
        return newsList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return ViewHolderHeader(
                LayoutInflater.from(parent.context).inflate(R.layout.banner, parent, false)
                    .also {
                        (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                            true
                    }
            )
        }
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
        )
    }

    @SuppressLint("ShowToast")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.itemView.banner.apply {
                adapter = CarouselImageAdapter(bannerList) //设置适配器
                setCurrentItem(1, false)
//                addBannerLifecycleObserver() //添加生命周期观察者
                setBannerRound(BannerUtils.dp2px(10f)) //圆角
                addPageTransformer(AlphaPageTransformer()) //添加切换效果
                setIndicator(CircleIndicator(context)) //设置指示器
            }
        }
        if (holder is ViewHolder) {
            val newsList = newsList[position - 1]
            with(holder.itemView) {
                visit_count.text = newsList.favorCount.toString()
                new_title.text = newsList.newTitle
                new_author.text = newsList.author
                new_date.text = newsList.date
                new_img.load(newsList.newTitleImgUrl) {
                    // 淡入淡出
                    crossfade(true)
                    transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
                    placeholder(R.drawable.ic_baseline_photo_24)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
                new_collect.setOnClickListener {
                    GlobalScope.launch {
                        try {
                            val collectNews = HomeRetrofitUtil.api.getCollectNews(
                                nid = newsList.id.toString(),
                                token = dataStoreRead("TOKEN")
                            )
                            when (collectNews.code) {
                                200 -> {
                                    Thread {
                                        Looper.prepare()
                                        DialogUtil.alertDialog(holder.itemView.context, "收藏成功")
                                        Looper.loop()
                                    }.start()
                                }
                                else -> {
                                    Thread {
                                        Looper.prepare()
                                        DialogUtil.alertDialog(holder.itemView.context, "收藏失败")
                                        Looper.loop()
                                    }.start()
                                }
                            }
                        } catch (e: Exception) {
                            Thread {
                                Looper.prepare()
                                DialogUtil.alertDialog(holder.itemView.context, "请求失败")
                                Looper.loop()
                            }.start()
                        }
                    }
                }
            }
            holder.itemView.setOnClickListener {
                val intent = Intent()
                intent.setClass(holder.itemView.context, NewsActivity::class.java)
                intent.putExtra("NEWS", Gson().toJson(newsList))
                holder.itemView.context.startActivity(intent)
            }
        }

    }

    class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}