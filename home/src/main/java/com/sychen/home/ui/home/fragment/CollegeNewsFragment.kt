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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sychen.basic.MyApplication
import com.sychen.home.R
import com.sychen.home.ui.home.HomeViewModel
import com.sychen.home.ui.home.NewsListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_college_news.*
import kotlinx.android.synthetic.main.fragment_news_push.*
import kotlinx.android.synthetic.main.fragment_niit_news.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CollegeNewsFragment : Fragment() {
    companion object {
        const val NewsType = 3
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val collegeNewsAdapter by lazy {
        CollegeNewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_college_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        college_news_recyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = collegeNewsAdapter
            college_news_swipe.isRefreshing = false
            collegeNewsAdapter.withLoadStateFooter(FooterAdapter { collegeNewsAdapter.refresh() })
        }
        college_news_swipe.setOnRefreshListener {
            viewModel.getPagingData(NewsType)
        }
        lifecycleScope.launch {
            viewModel.getPagingData(NewsType).collect {
                collegeNewsAdapter.submitData(it)
            }
        }
        collegeNewsAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    Log.e(MyApplication.TAG, "onActivityCreated: LoadState.NotLoading")
                }
                is LoadState.Loading -> {
                    Log.e(MyApplication.TAG, "onActivityCreated: LoadState.Loading")
                }
                is LoadState.Error -> {
                    Log.e(MyApplication.TAG, "onActivityCreated: LoadState.Error")
                }
            }
        }
    }

}