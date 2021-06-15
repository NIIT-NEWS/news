package com.sychen.basic.network

import com.sychen.basic.network.BaseResult
import retrofit2.http.GET

interface API {
    @GET("/net/get")
    suspend fun getNet() : BaseResult<String>
}