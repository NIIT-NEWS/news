package com.sychen.basic.network

object MainRetrofit {
    val api by lazy {
        RetrofitUtil.getRetrofit().create(API::class.java)
    }
}