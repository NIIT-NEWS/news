package com.sychen.search.network;

import com.sychen.basic.network.BaseResult;
import com.sychen.basic.network.RetrofitUtil;
import com.sychen.search.network.model.SearchNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("news/search-news")
    public Call<BaseResult<List<SearchNews>>> searchNews(@Query("title") String title);

}
