package com.sychen.login.network.model


import com.google.gson.annotations.SerializedName

data class Login(
        @SerializedName("code")
        val code: Int = 0, // 200
        @SerializedName("data")
        val `data`: Data = Data()
) {
        data class Data(
                @SerializedName("avatar")
                val avatar: String = "", // null
                @SerializedName("id")
                val id: Int = 0, // 1
                @SerializedName("role")
                val role: String = "", // admin
                @SerializedName("token")
                val token: String = "", // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lU3RhbXAiOjE2MTgwNjY1MTk4MDgsInVzZXJQYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJOYW1lIjoiY3N5In0.to3IV334qQbtSGYK1SYT6vGbsOEFSaeCHu9yzT3_w8U
                @SerializedName("username")
                val username: String = "" // csy
        )
}