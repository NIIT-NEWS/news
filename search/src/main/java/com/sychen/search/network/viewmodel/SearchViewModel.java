package com.sychen.search.network.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sychen.basic.network.BaseResult;
import com.sychen.search.network.SearchNewsRetrofit;
import com.sychen.search.network.model.SearchNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    public static final String TAG = "TAG";
    private MutableLiveData<List<SearchNews>> _searchNews = new MutableLiveData<>();
    public LiveData<List<SearchNews>> searchNews = _searchNews;

    public void searchNews(String title){
        Call<BaseResult<List<SearchNews>>> baseResultCall = new SearchNewsRetrofit().api.searchNews(title);
        baseResultCall.enqueue(new Callback<BaseResult<List<SearchNews>>>() {
            @Override
            public void onResponse(Call<BaseResult<List<SearchNews>>> call, Response<BaseResult<List<SearchNews>>> response) {
                _searchNews.postValue(response.body().getData());
                Log.e(TAG, "onResponse: 网络请求成功");
            }

            @Override
            public void onFailure(Call<BaseResult<List<SearchNews>>> call, Throwable t) {
                Log.e(TAG, "onFailure: 网络请求失败"+t.toString() );
            }
        });
    }
}
