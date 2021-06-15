package com.sychen.search.network

import com.sychen.basic.network.BaseResult
import com.sychen.search.network.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("/news/search-news")
    suspend fun getNews(@Query("title") title:String): BaseResult<List<News>>
}
