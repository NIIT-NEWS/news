package com.sychen.niitvideo.network;

import com.sychen.basic.network.BaseResult;
import com.sychen.niitvideo.network.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("/video/getVideos")
    Call<BaseResult<List<Video>>> getVideos();
}
