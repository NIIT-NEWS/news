package com.sychen.login.network

import com.sychen.login.network.model.Login
import com.sychen.login.network.model.VerifyToken
import retrofit2.Call
import retrofit2.http.*

interface API {
    @POST("/user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<Login>

    @Headers("Accept-Language")
    @GET("/user/verify-token")
    fun verifyToken(
         token: String
    ): Call<VerifyToken>
}