package com.sychen.user.network.model

data class User(
    var userid: Int = 0,
    var username: String="",
    var password: String="",
    var avatar: String="",
    var role: String="",
)
