package com.sychen.jwxx.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sychen.basic.util.StatusBarHeight
import com.sychen.jwxx.R
import com.sychen.jwxx.ui.download.DownloadFragment
import com.sychen.jwxx.ui.publicnotice.NoticeFragment
import kotlinx.android.synthetic.main.fragment_jwxx.*

class JwxxFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jwxx, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        jwxx_main_guideline.setGuidelineBegin(StatusBarHeight.get())
        mainViewPager.apply {
            adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun getItemCount() = 2
                override fun createFragment(position: Int) = when (position) {
                    1 -> NoticeFragment()
                    else -> DownloadFragment()
                }
            }
            setCurrentItem(1,false) //false 代表平滑滚动
        }
        TabLayoutMediator(tabLayout,mainViewPager){ tab: TabLayout.Tab, i:Int->
            tab.text=when(i){
                1->"公共通知"
                else->"公共下载"
            }
        }.attach()
    }
}