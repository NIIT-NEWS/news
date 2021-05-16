package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class Review(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: List<Data> = listOf(),
        @SerializedName("message")
        val message: String = "" // success
) {
        data class Data(
                @SerializedName("r_id")
                val id:Int,
                @SerializedName("avatar")
                val avatar: String = "", // http://ccsy.oss-cn-beijing.aliyuncs.com/my_file/ce8de60c-2b94-42ed-8f94-14fdf7c30113.jpg
                @SerializedName("r_content")
                val content: String = "", // aaaaaaaaaaaaaaaaa
                @SerializedName("r_date")
                val date: String = "", // 2021-4-20
                @SerializedName("username")
                val username: String = "" , // 2021-4-20
                @SerializedName("nickname")
                val nickname: String = "" // saaaaaaaa
        )
}