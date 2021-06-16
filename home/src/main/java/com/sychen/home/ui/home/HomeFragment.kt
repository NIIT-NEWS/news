package com.sychen.home.ui.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.home.R
import com.sychen.home.services.LocationService
import com.sychen.home.ui.home.fragment.CollegeNewsFragment
import com.sychen.home.ui.home.fragment.KyFragment
import com.sychen.home.ui.home.fragment.NewsPushFragment
import com.sychen.home.ui.home.fragment.NiitNewsFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.search.*
import java.util.*

class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_viewpager.apply {
            adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun getItemCount() = 4
                override fun createFragment(position: Int) = when (position) {
                    1 -> NiitNewsFragment()
                    2 -> NewsPushFragment()
                    3 -> CollegeNewsFragment()
                    else -> KyFragment()
                }
            }
            setCurrentItem(1,false) //false 代表平滑滚动
        }
        TabLayoutMediator(home_tablayout,home_viewpager){ tab: TabLayout.Tab, i:Int->
            tab.text=when(i){
                1->"学校要闻"
                2->"新闻速递"
                3->"院部风采"
                else->"抗议专栏"
            }
        }.attach()
        search_text.setOnClickListener { v ->
            val makeScaleUpAnimation =
                ActivityOptionsCompat.makeScaleUpAnimation(v, v.width, v.height, 0, 0)
            ARouter.getInstance().build(ARouterUtil.START_SEARCH_ACTIVITY)
                .withString("SEARCH_TEXT", edit_query.text?.toString()?.trim())
                .withOptionsCompat(makeScaleUpAnimation)
                .navigation()
        }
    }

    private fun uploadLocation() {
        val intent = Intent(requireContext(), LocationService::class.java)
        requireActivity().startService(intent)
        requireActivity().bindService(
            intent, object : ServiceConnection {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as LocationService.MyBinder).service.locationData.observe(
                        requireActivity(),
                        {
                            var lon = ""
                            var lat = ""
                            it.forEach { s ->
                                if (',' == s) {
                                    lon = it.substring(0, it.indexOf(s))
                                    lat = it.substring(it.indexOf(s) + 1)
                                }
                            }
                            Timer().schedule(object : TimerTask() {
                                override fun run() {
                                    viewModel.uploadLocation(lon, lat, "1", "10:54")
                                }
                            }, Date(), 60000)

                        })
                }

                override fun onServiceDisconnected(name: ComponentName?) {

                }
            },
            Context.BIND_AUTO_CREATE
        )
    }
}