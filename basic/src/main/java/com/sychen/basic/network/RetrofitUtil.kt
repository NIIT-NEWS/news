package com.sychen.basic.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.MyApplication
import com.sychen.basic.MyApplication.Companion.getContext
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://39.101.177.93:8098/"
const val TEST_BASE_URL = "http://sychen.cn1.utools.club"
const val DEFAULT_TIME_OUT: Long = 30

object RetrofitUtil {

    private val httpBuilder: OkHttpClient.Builder
        get() {
            val cacheFile = File(getContext().cacheDir, "cache")
            val cache = Cache(cacheFile, 1024 * 1024 * 50)// 50M 的缓存大小
            // create http client
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()

                    //header
                    val request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build()

                    return@Interceptor chain.proceed(request)
                })
                .cache(cache)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接 超时时间
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//读操作 超时时间
                .retryOnConnectionFailure(true)//错误重连

            // log interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient
        }
    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .create()
    private var INSTANCE: Retrofit? = null

    fun getRetrofit(): Retrofit {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        val instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpBuilder.build())
            .build()
        INSTANCE = instance
        return instance
    }

}