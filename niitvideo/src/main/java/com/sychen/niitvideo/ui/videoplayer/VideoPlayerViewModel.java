package com.sychen.niitvideo.ui.videoplayer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sychen.basic.network.BaseResult;
import com.sychen.basic.network.RetrofitUtil;
import com.sychen.niitvideo.network.API;
import com.sychen.niitvideo.network.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoPlayerViewModel extends ViewModel {
    private MutableLiveData<List<Video>> _videoInfo = new MutableLiveData<>();
    public static final String TAG = "TAG";

    public LiveData<List<Video>> getVideos() {
        RetrofitUtil.INSTANCE.getRetrofit().create(API.class).getVideos().enqueue(new Callback<BaseResult<List<Video>>>() {
            @Override
            public void onResponse(Call<BaseResult<List<Video>>> call, Response<BaseResult<List<Video>>> response) {
                if (response.isSuccessful()){
                    _videoInfo.postValue(response.body().getData());
                    Log.e(TAG, "获取视频资讯成功");
                }
            }

            @Override
            public void onFailure(Call<BaseResult<List<Video>>> call, Throwable t) {
                Log.e(TAG, "获取视频资讯失败"+t.getMessage());
            }
        });
        return _videoInfo;
    }
}