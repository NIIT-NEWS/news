package com.sychen.user.network

import com.sychen.basic.network.BaseResult
import com.sychen.user.network.model.UpdateUser
import com.sychen.user.network.model.UploadAvatar
import com.sychen.user.network.model.UserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("/user/queryuser")
    fun getUserById(@Query("id") id: String): Call<UserInfo>

    @Multipart
    @POST("/user/upload-avatar")
    fun uploadAvatar(@Part("description") description: RequestBody, @Part file: MultipartBody.Part ): Call<UploadAvatar>

    @POST("/user/update-user")
    fun updateUserInfo(@Body user: RequestBody): Call<UpdateUser>

    @GET("/user/query-name")
    fun verifyName(@Query("username") name:String):Call<BaseResult<Any>>
}