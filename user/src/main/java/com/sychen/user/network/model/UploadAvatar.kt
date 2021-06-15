package com.sychen.user.network.model


import com.google.gson.annotations.SerializedName

data class UploadAvatar(
    @SerializedName("url")
    val url: String = "" // http://ccsy.oss-cn-beijing.aliyuncs.com/my_file/414ae547-76f6-47bb-b66c-3c627bbff71a.jpg
)