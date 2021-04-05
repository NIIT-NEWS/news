package com.sychen.home.network

import com.sychen.basic.NewRetrofit

object HomeRetrofitUtil {
    val api: API = NewRetrofit.create(API::class.java)
}