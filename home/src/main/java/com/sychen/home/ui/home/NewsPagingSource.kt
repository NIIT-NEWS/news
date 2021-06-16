package com.sychen.home.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.NiitNews

class NewsPagingSource(val type: Int) : PagingSource<Int, NiitNews.News>() {
    override fun getRefreshKey(state: PagingState<Int, NiitNews.News>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NiitNews.News> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val newsPage = HomeRetrofitUtil
                .api.getNewsPage(pageNum = page, pageSize = pageSize, type = type)
            val repoItems = newsPage.data.list
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}