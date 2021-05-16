package com.sychen.user.network

import com.sychen.basic.network.RetrofitUtil

object UserRetrofitUtil {
    val api: API = RetrofitUtil.create(API::class.java)
}