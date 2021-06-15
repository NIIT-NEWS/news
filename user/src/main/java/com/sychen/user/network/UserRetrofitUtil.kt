package com.sychen.user.network

import com.sychen.basic.network.RetrofitUtil

object UserRetrofitUtil {
    val api by lazy {
        RetrofitUtil.getRetrofit().create(API::class.java)
    }
}