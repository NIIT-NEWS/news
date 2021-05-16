package com.sychen.user.network.model


import com.google.gson.annotations.SerializedName

data class UploadAvatar(
    @SerializedName("code")
        val code: Int = 0, // 200
    @SerializedName("data")
        val `data`: Data = Data(),
    @SerializedName("message")
        val message: String = "" // 上传成功
) {
        data class Data(
                @SerializedName("url")
                val url: String = "" // http://ccsy.oss-cn-beijing.aliyuncs.com/my_file/414ae547-76f6-47bb-b66c-3c627bbff71a.jpg
        )
}