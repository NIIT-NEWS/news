package com.sychen.home.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sychen.home.network.model.NiitNews
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 20

    fun getPagingData(type:Int): Flow<PagingData<NiitNews.News>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(type) }
        ).flow
    }

}
