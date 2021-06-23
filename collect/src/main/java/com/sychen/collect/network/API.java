package com.sychen.collect.network;

import com.sychen.basic.network.BaseResult;
import com.sychen.collect.network.model.CollectNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface API {
    @GET("collect/getNews")
    Call<BaseResult<List<CollectNews>>> collectNews(@Header("token") String token);
}
