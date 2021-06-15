package com.sychen.jwxx.network

import com.sychen.basic.network.BASE_URL
import com.sychen.basic.network.RetrofitUtil
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

object JwxxRetrofitDownload {
     val retrofit: UrlService? = Retrofit.Builder()
        .baseUrl("http://jwxx.niit.edu.cn/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UrlService::class.java)

}

//Service的写法
interface UrlService {
    //通用下载
    @Streaming
    @GET
    suspend fun downloadFile(@Url url:String): Response<ResponseBody>
}