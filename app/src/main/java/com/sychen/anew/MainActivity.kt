package com.sychen.anew

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sychen.anew.ui.dashboard.DashboardFragment
import com.sychen.anew.ui.home.HomeFragment
import com.sychen.anew.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager2()
        initBottomNav()
    }


    /**
     * 初始化主页面的viewpager2，主要目的是为了和底部导航进行联动
     * 注册viewpager2滑动监听
     */
    private fun initViewPager2(){
        mainViewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount() = 3
                override fun createFragment(position: Int) = when (position) {
                    0 -> HomeFragment()
                    1 -> DashboardFragment()
                    else -> NotificationsFragment()
                }
            }
            setCurrentItem(0, false) //false 代表平滑滚动
        }
        mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                nav_view.menu.getItem(position).isChecked = true
            }
        })
    }
    /**
     * 当点击到菜单中的item时，调用viewPage中的setCurrentItem方法切换到对应界面
     */
    private fun initBottomNav() {
        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> mainViewPager.currentItem = 0
                R.id.navigation_dashboard -> mainViewPager.currentItem = 1
                R.id.navigation_notifications -> mainViewPager.currentItem = 2
            }
            false
        }
    }
}