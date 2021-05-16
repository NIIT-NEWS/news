package com.sychen.anew.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sychen.anew.R
import com.sychen.anew.ui.dashboard.DashboardFragment
import com.sychen.anew.ui.home.HomeFragment
import com.sychen.anew.ui.user.UserFragment
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.SharedPreferencesUtil
import com.sychen.basic.util.SharedPreferencesUtil.sharedPreferencesLoad
import com.sychen.basic.util.SharedPreferencesUtil.sharedPreferencesSave
import com.sychen.basic.util.dataStoreSave
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
@Route(path = ARouterUtil.START_MAIN_ACTIVITY)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        initViewPager2()
        initBottomNav()
        initHomeCard()
    }

    private fun initHomeCard() {
        if (sharedPreferencesLoad<Int>("CARD_RADIUS")==null){
            sharedPreferencesSave("CARD_RADIUS", 40)
            sharedPreferencesSave("CARD_ELEVATION", 20)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 初始化主页面的viewpager2，主要目的是为了和底部导航进行联动
     * 注册viewpager2滑动监听
     */
    private fun initViewPager2() {
        mainViewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount() = 3
                override fun createFragment(position: Int) = when (position) {
                    0 -> HomeFragment()
                    1 -> DashboardFragment()
                    else -> UserFragment()
                }
            }
            setCurrentItem(0, false) //false 代表平滑滚动
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    nav_view.menu.getItem(position).isChecked = true
                }
            })
            isUserInputEnabled = false //禁止滑动
        }
    }

    /**
     * 当点击到菜单中的item时，调用viewPage中的setCurrentItem方法切换到对应界面
     */
    private fun initBottomNav() {
        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> mainViewPager.currentItem = 0
                R.id.navigation_dashboard -> mainViewPager.currentItem = 1
                R.id.navigation_user -> mainViewPager.currentItem = 2
            }
            false
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.type) {
            MessageType.TypeTwo -> {
                when(event.getString()){
                    "onStart"->{
                        nav_view.visibility = View.VISIBLE
                    }
                    "onStop"->{
                        nav_view.visibility = View.GONE
                    }
                }
            }
            MessageType.TypeOne -> {
            }
        }
    }
}