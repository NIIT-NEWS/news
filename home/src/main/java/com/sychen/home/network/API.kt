package com.sychen.home.network

import com.sychen.basic.network.BaseResult
import com.sychen.home.network.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {

    @GET("/news/getallnews")
    suspend fun getNewsPage(
        @Query("type") type:Int,
        @Query("pageNum") pageNum:Int,
        @Query("pageSize") pageSize:Int
    ): BaseResult<NiitNews>

    @POST("/location/uploadlocation")
    fun uploadLocation(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
        @Query("uid") uid: String,
        @Query("time") time: String,
    ): Call<Location>

    @GET("/review/query-review")
    fun getReviewByNid(@Query("nid") nid:String): Call<Review>

    @POST("/review/insert-review")
    fun uploadReview(@Body review:RequestBody):Call<InsertReview>
    @GET("/banner/getBanners")
    fun getBanner():Call<Banner>
}