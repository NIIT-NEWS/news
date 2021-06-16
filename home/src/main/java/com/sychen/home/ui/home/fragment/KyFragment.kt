package com.sychen.home.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sychen.home.R
import com.sychen.home.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_ky.*
import kotlinx.android.synthetic.main.fragment_news_push.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class KyFragment : Fragment() {
    private val viewModel by lazy {
        HomeViewModel()
    }

    private val kyAdapter by lazy {
        KyAdapter()
    }
    companion object{
        const val NewsType = 4
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ky, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ky_recyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = kyAdapter
            ky_swipe.isRefreshing = false
            kyAdapter.withLoadStateFooter(FooterAdapter{kyAdapter.refresh()})
        }
        ky_swipe.setOnRefreshListener {
            viewModel.getPagingData(NewsType)
        }
        lifecycleScope.launch {
            viewModel.getPagingData(NewsType).collect {
                kyAdapter.submitData(it)
            }
        }
    }

}