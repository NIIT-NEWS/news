package com.sychen.user.network.model


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("avatar")
    var avatar: String = "", // http://ccsy.oss-cn-beijing.aliyuncs.com/my_file/c11c425b-547f-4edc-9fea-3cf2fcb4d3fb.jpg
    @SerializedName("id")
    var id: Int = 0, // 1
    @SerializedName("password")
    var password: String = "", // 123456
    @SerializedName("role")
    var role: String = "", // admin
    @SerializedName("token")
    var token: String = "", // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTgzMjU1NDkxOTMsInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.ocF2nWYOLCRUbmyI6q4634RVpIzaTbLJn9tNhZiErrg
    @SerializedName("username")
    var username: String = "",// csy, // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTgzMjU1NDkxOTMsInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.ocF2nWYOLCRUbmyI6q4634RVpIzaTbLJn9tNhZiErrg
    @SerializedName("nickname")
    var nickname: String = "" // csy
)