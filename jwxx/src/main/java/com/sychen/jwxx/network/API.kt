package com.sychen.jwxx.network

import com.sychen.basic.network.BaseResult
import com.sychen.basic.network.RetrofitUtil
import com.sychen.jwxx.network.model.PublicData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

object JwxxRetrofitUtil {
    val api: API = RetrofitUtil.getRetrofit().create(API::class.java)
}

interface API {
    @GET("/public/getAllNotice")
    suspend fun getAllNotice(): BaseResult<List<PublicData>>

    @GET("/public/getAllDownload")
    fun getAllDownload(): Call<BaseResult<List<PublicData>>>
}