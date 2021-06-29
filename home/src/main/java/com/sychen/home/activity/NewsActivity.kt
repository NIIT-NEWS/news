package com.sychen.home.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.StatusBarHeight
import com.sychen.home.R
import com.sychen.home.network.model.NiitNews
import com.sychen.home.ui.newdetails.NewDetailsFragment
import com.sychen.home.ui.review.ReviewFragment
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity() {
    private lateinit var news: NiitNews.News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initViewPager()
    }

    private fun initViewPager() {
        news = Gson().fromJson(intent.getStringExtra("NEWS"), NiitNews.News::class.java)
        new_viewpager.apply {
            adapter = object : FragmentStateAdapter(this@NewsActivity) {
                override fun getItemCount() = 2

                @RequiresApi(Build.VERSION_CODES.O)
                override fun createFragment(position: Int) = when (position) {
                    0 -> NewDetailsFragment().getInstance(news)
                    else -> ReviewFragment().getInstance(news)
                }
            }
            setCurrentItem(0, false) //false 代表平滑滚动
        }
        initTopGuideLine()
    }

    private fun initTopGuideLine() {
        new_guideline.setGuidelineBegin(StatusBarHeight.get())
    }


}