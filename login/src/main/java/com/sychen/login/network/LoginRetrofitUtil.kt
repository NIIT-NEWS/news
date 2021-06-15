package com.sychen.login.network

import com.sychen.basic.network.RetrofitUtil

object LoginRetrofitUtil {
    val api: API by lazy {
        RetrofitUtil.getRetrofit().create(API::class.java)
    }
}