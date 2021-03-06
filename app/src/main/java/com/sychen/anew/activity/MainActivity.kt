package com.sychen.anew.activity

import android.os.Bundle
import android.view.View
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sychen.anew.R
import com.sychen.anew.ui.home.HomeFragment
import com.sychen.anew.ui.user.UserFragment
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.PermissionUtil
import com.sychen.basic.util.SharedPreferencesUtil.sharedPreferencesLoad
import com.sychen.basic.util.SharedPreferencesUtil.sharedPreferencesSave
import com.sychen.jwxx.ui.main.JwxxFragment
import com.sychen.niitvideo.ui.videoplayer.VideoFragment
import com.sychen.niitvideo.ui.videoplayer.VideoPlayerFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = ARouterUtil.START_MAIN_ACTIVITY)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager2()
        initBottomNav()
        initHomeCard()
        PermissionUtil.runtimePermission("WRITE_EXTERNAL_STORAGE")
        PermissionUtil.runtimePermission("READ_EXTERNAL_STORAGE")
        PermissionUtil.runtimePermission("RECEIVE_BOOT_COMPLETED")
    }



    private fun initHomeCard() {
        if (sharedPreferencesLoad<Int>("CARD_RADIUS") == null) {
            sharedPreferencesSave("CARD_RADIUS", 40)
            sharedPreferencesSave("CARD_ELEVATION", 20)
        }
    }

    /**
     * 初始化主页面的viewpager2，主要目的是为了和底部导航进行联动
     * 注册viewpager2滑动监听
     */
    private fun initViewPager2() {
        mainViewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount() = 4
                override fun createFragment(position: Int) = when (position) {
                    0 -> HomeFragment()
                    1 -> VideoPlayerFragment()
                    2 -> JwxxFragment()
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
                R.id.navigation_video -> mainViewPager.currentItem = 1
                R.id.navigation_dashboard -> mainViewPager.currentItem = 2
                R.id.navigation_user -> mainViewPager.currentItem = 3
            }
            false
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onMessageEvent(event: MessageEvent) {
        when (event.type) {
            MessageType.TypeTwo -> {
                when (event.getString()) {
                    "onStart" -> {
                        nav_view.visibility = View.VISIBLE
                    }
                    "onStop" -> {
                        nav_view.visibility = View.GONE
                    }
                }
            }
            MessageType.TypeOne -> {
            }
        }
    }
}