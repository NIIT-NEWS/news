package com.sychen.login.network.model


import com.google.gson.annotations.SerializedName

data class VerifyToken(
    @SerializedName("timeStamp")
    val timeStamp: String = "", // 1618071953934
    @SerializedName("username")
    val userName: String = "", // csy
    @SerializedName("password")
    val userPassword: String = "",
    @SerializedName("userid")
    val userId: String = ""
)