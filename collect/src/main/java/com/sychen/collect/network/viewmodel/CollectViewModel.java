package com.sychen.collect.network.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sychen.basic.network.BaseResult;
import com.sychen.collect.network.CollectNewsRetrofit;
import com.sychen.collect.network.model.CollectNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectViewModel extends ViewModel {
    public static final String TAG = "TAG";
    private MutableLiveData<List<CollectNews>> _collectNews = new MutableLiveData<>();
    public LiveData<List<CollectNews>> collectNews = _collectNews;

    public void collectNews(String token){
        Call<BaseResult<List<CollectNews>>> baseResultCall = new CollectNewsRetrofit().api.collectNews(token);
        baseResultCall.enqueue(new Callback<BaseResult<List<CollectNews>>>() {
            @Override
            public void onResponse(Call<BaseResult<List<CollectNews>>> call, Response<BaseResult<List<CollectNews>>> response) {
                _collectNews.postValue(response.body().getData());
                Log.e(TAG, "获取收藏信息: 网络请求成功");
            }

            @Override
            public void onFailure(Call<BaseResult<List<CollectNews>>> call, Throwable t) {
                Log.e(TAG, "获取收藏信息: 网络请求失败"+t.toString() );
            }
        });
    }
}