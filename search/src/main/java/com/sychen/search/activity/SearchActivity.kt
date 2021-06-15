package com.sychen.search.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.basic.ARouterUtil
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.StatusBarHeight
import com.sychen.search.R
import com.sychen.search.adapter.NewsListAdapter
import com.sychen.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

@Route(path = ARouterUtil.START_SEARCH_ACTIVITY)
class SearchActivity : BaseActivity() {

    @Autowired(name = "SEARCH_TEXT")
    @JvmField
    var searchText: String = ""

    private val viewModel by lazy {
        SearchViewModel()
    }

    private val newsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {
        topline.setGuidelineBegin(StatusBarHeight.get())
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            adapter = newsListAdapter
        }
        viewModel.getNewsList(searchText).observe(this, {
            newsListAdapter.submitList(it)
        })
        toolbar.setNavigationOnClickListener {
            ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY).navigation()
        }
    }
}