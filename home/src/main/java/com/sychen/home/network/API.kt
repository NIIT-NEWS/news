package com.sychen.home.network

import com.sychen.home.network.model.Location
import com.sychen.home.network.model.News
import com.sychen.home.network.model.NewsC
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {

    @GET("news/getallnews")
    fun getAllNewsC() :Call<NewsC>

    @POST("/location/uploadlocation")
    fun uploadLocation(
        @Query("lon") lon:String,
        @Query("lat") lat:String,
        @Query("uid") uid:String,
        @Query("time") time:String,
    ):Call<Location>

}