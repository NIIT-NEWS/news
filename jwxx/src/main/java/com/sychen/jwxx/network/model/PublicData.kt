package com.sychen.jwxx.network.model


import com.google.gson.annotations.SerializedName

data class PublicData(
    @SerializedName("id")
    val id: Int = 0, // 1
    @SerializedName("title")
    val title: String = "", // 2020-2021-2学期重修安排
    @SerializedName("url")
    val url: String = "" // http://jwxx.niit.edu.cn/_data/read_detail_news.aspx?tid=593
)