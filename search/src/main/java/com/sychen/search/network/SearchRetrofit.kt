package com.sychen.search.network

import com.sychen.basic.network.RetrofitUtil

object SearchRetrofit {
    val api by lazy {
        RetrofitUtil.getRetrofit().create(API::class.java)
    }
}