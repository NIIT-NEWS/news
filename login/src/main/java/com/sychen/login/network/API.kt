package com.sychen.login.network

import com.sychen.login.network.model.*
import retrofit2.Call
import retrofit2.http.*

interface API {
    @POST("/user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<Login>

    @GET("/user/verify-token")
    fun verifyToken(
        @Header("token") token: String
    ): Call<VerifyToken>

}