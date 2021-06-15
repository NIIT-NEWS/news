package com.sychen.home.network

import com.sychen.basic.network.RetrofitUtil

object HomeRetrofitUtil {
    val api by lazy {
        RetrofitUtil.getRetrofit().create(API::class.java)
    }
}