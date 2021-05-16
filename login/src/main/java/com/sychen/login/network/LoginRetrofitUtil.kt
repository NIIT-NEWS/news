package com.sychen.login.network

import com.sychen.basic.network.RetrofitUtil

object LoginRetrofitUtil {
    val api: API = RetrofitUtil.create(API::class.java)
}