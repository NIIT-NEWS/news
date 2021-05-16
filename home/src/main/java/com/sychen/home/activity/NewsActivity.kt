package com.sychen.home.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.StatusBarHeight
import com.sychen.home.R
import com.sychen.home.network.model.New
import com.sychen.home.ui.newdetails.NewDetailsFragment
import com.sychen.home.ui.review.ReviewFragment
import kotlinx.android.synthetic.main.activity_news.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NewsActivity : AppCompatActivity() {
    private lateinit var news: New.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initViewPager()
    }

    private fun initViewPager() {
        try {
            news = Gson().fromJson(intent.getStringExtra("NEWS"), New.Data::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "NewDetailsFragment-onActivityCreated: ${e.message}")
        }
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