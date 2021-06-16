package com.sychen.home.ui.home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.home.R
import com.sychen.home.ui.home.HomeViewModel
import com.sychen.home.ui.home.NewsListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_news_push.*
import kotlinx.android.synthetic.main.fragment_niit_news.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsPushFragment : Fragment() {
    companion object{
        const val NewsType = 2
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private val newsPushAdapter by lazy {
        NewsPushAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_push, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        news_push_recyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = newsPushAdapter
            news_push_swipe.isRefreshing = false
            newsPushAdapter.withLoadStateFooter(FooterAdapter{newsPushAdapter.refresh()})
        }
        news_push_swipe.setOnRefreshListener {
            viewModel.getPagingData(NewsType)
        }
        lifecycleScope.launch {
            viewModel.getPagingData(NewsType).collect {
                newsPushAdapter.submitData(it)
            }
        }
        newsPushAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    Log.e(TAG, "onActivityCreated: " )
                }
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                }
            }
        }
    }

}