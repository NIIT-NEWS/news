package com.sychen.login.network

import com.sychen.basic.network.BaseResult
import com.sychen.login.network.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface API {
    @POST("/user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): BaseResult<UserInfo>

    @POST("/user/verify-token")
    suspend fun verifyToken(
        @Header("token") token: String
    ): BaseResult<VerifyToken>

    @POST("/user/register")
    suspend fun register(@Body user: RequestBody): BaseResult<UserInfo>
}