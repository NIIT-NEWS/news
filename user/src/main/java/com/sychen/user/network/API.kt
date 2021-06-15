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
    suspend fun getUserInfo(@Header("token") token: String): BaseResult<UserInfo>

    @Multipart
    @POST("/user/upload-avatar")
    suspend fun uploadAvatar(@Part("description") description: RequestBody, @Part file: MultipartBody.Part ): BaseResult<UploadAvatar>

    @POST("/user/update-user")
    fun updateUserInfo(@Body user: RequestBody): Call<UpdateUser>

    @PUT("/user/update-nickname")
    suspend fun updateName(@Header("token") token: String,
                   @Query("nickname") nickname:String,):BaseResult<Any>

    @PUT("/user/update-avatar")
    suspend fun updateAvatar(@Header("token") token: String,
                   @Query("avatar") avatar:String,):BaseResult<Any>

}