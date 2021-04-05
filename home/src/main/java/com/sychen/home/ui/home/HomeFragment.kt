package com.sychen.home.ui.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sychen.basic.Utils.Show
import com.sychen.home.R
import com.sychen.home.services.LocationService
import kotlinx.android.synthetic.main.home_fragment.*
import java.time.LocalTime
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var newsListRecyclerAdapter: NewsListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        uploadLocation()
        newsListRecyclerAdapter = NewsListRecyclerAdapter()
        home_recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            newsListRecyclerAdapter.also { adapter = it }
        }
        viewModel.newsInfo.observe(requireActivity(), {
            newsListRecyclerAdapter.submitList(it)
            swipe.isRefreshing = false
        })
        swipe.setOnRefreshListener {
            viewModel.getAllNews()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Show.showLog("HomeFragment:onDestroy")
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
                                    viewModel.uploadLocation(lon,lat,"1","10:54")
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

    fun getStringIndex(str: String): Int {
        var flag = -1
        for (i in str.indices) {
            if (str[i] == ',') {
                println("------")
                flag = i
            }
        }
        return flag
    }

}