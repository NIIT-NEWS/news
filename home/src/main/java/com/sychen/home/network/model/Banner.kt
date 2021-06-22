package com.sychen.home.network.model


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    val id: Int = 0, // 3
    @SerializedName("imgUrl")
    val imgUrl: String = "", // http://www.niit.edu.cn//_upload/article/images/23/4f/0921548f4248bb37da5468d820dd/6c480afb-dba6-4bac-b5c9-42156463425d.jpg
    @SerializedName("isBanner")
    val isBanner: Int = 0 // 1
)