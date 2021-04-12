package com.sychen.basic

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

const val BASE_URL="http://sychen.cn1.utools.club/"
object RetrofitUtil {
    private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}