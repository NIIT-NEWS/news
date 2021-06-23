package com.sychen.collect.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectNewsRetrofit {
    public API api;
    public CollectNewsRetrofit() {
        api = new Retrofit.Builder()
                .baseUrl("http://39.101.177.93:8098/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API.class);
    }
}
