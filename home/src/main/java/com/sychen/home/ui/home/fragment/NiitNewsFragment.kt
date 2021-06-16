package com.sychen.home.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sychen.home.R
import com.sychen.home.ui.home.HomeViewModel
import com.sychen.home.ui.home.NewsListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_niit_news.*
import kotlinx.android.synthetic.main.home_fragment.*

class NiitNewsFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var newsListRecyclerAdapter: NewsListRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_niit_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAllNews(1,0,10).observe(requireActivity(),{
            newsListRecyclerAdapter = NewsListRecyclerAdapter(it.list, viewModel.bannerInfo.value!!)
            niit_news_swipe.isRefreshing = false
            niit_news_recyclerview.apply {
                layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                adapter = newsListRecyclerAdapter
            }
        })
        niit_news_swipe.setOnRefreshListener {
            viewModel.getAllNews(1,0,10)
        }
    }

}