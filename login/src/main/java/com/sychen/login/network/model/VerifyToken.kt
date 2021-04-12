package com.sychen.login.network.model


import com.google.gson.annotations.SerializedName

data class VerifyToken(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: Data = Data()
) {
        data class Data(
                @SerializedName("timeStamp")
                val timeStamp: String = "", // 1618071953934
                @SerializedName("userName")
                val userName: String = "", // csy
                @SerializedName("userPassword")
                val userPassword: String = "" // 123456
        )
}