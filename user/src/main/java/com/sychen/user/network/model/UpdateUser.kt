package com.sychen.user.network.model


import com.google.gson.annotations.SerializedName

data class UpdateUser(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: Data = Data(),
        @SerializedName("message")
        val message: String = "" // 修改成功
) {
        data class Data(
                @SerializedName("avatar")
                var avatar: String = "", // null
                @SerializedName("id")
                var id: Int = 0, // 1
                @SerializedName("password")
                var password: String = "", // 123456
                @SerializedName("role")
                var role: String = "", // admin
                @SerializedName("token")
                var token: String = "", // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTg1NjI1MTMwMzksInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.qmAcrSvupetpfRCmGqRhLVUZKQS51110AHcFv7Vgo2Q
                @SerializedName("username")
                var username: String = "" // csy
        )
}