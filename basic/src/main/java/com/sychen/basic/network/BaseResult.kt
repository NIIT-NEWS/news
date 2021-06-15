package com.sychen.basic.network

import com.google.gson.annotations.SerializedName

data class BaseResult<out T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
)