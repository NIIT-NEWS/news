package com.sychen.basic.network

import com.google.gson.annotations.SerializedName

data class BaseResult<T>(
    @SerializedName("code")
    val code:Int=0,
    @SerializedName("message")
    val message:String="",
    @SerializedName("data")
    val `data`:T
)