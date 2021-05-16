package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class InsertReview(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: Data = Data(),
        @SerializedName("message")
        val message: String = "" // success
) {
        data class Data(
                @SerializedName("content")
                val content: String = "", // 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
                @SerializedName("date")
                val date: String = "", // 2021-4-20
                @SerializedName("id")
                val id: Int = 0, // 8
                @SerializedName("newId")
                val newId: Int = 0, // 163
                @SerializedName("userId")
                val userId: Int = 0 // 57
        )
}