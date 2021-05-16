package com.sychen.user.network.model


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("code")
        val code: Int = 0, // 200
    @SerializedName("data")
        val `data`: Data = Data(),
    @SerializedName("message")
        val message: String = "" // 查询成功
) {
        data class Data(
                @SerializedName("avatar")
                val avatar: String = "", // http://ccsy.oss-cn-beijing.aliyuncs.com/my_file/c11c425b-547f-4edc-9fea-3cf2fcb4d3fb.jpg
                @SerializedName("id")
                val id: Int = 0, // 1
                @SerializedName("password")
                val password: String = "", // 123456
                @SerializedName("role")
                val role: String = "", // admin
                @SerializedName("token")
                val token: String = "", // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTgzMjU1NDkxOTMsInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.ocF2nWYOLCRUbmyI6q4634RVpIzaTbLJn9tNhZiErrg
                @SerializedName("username")
                val username: String = "" ,// csy, // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTgzMjU1NDkxOTMsInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.ocF2nWYOLCRUbmyI6q4634RVpIzaTbLJn9tNhZiErrg
                @SerializedName("nickname")
                val nickname: String = "" // csy
        )
}