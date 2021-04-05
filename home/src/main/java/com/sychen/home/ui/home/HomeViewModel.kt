package com.sychen.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.Utils.Show
import com.sychen.home.network.model.NewsC
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.Location
import com.sychen.home.network.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    private var _newsInfo = MutableLiveData<List<NewsC.Data>>()
    val newsInfo: LiveData<List<NewsC.Data>> = _newsInfo

    fun getAllNews() {
        HomeRetrofitUtil.api.getAllNewsC()
            .enqueue(object : Callback<NewsC> {
                override fun onResponse(
                    call: Call<NewsC>,
                    response: Response<NewsC>
                ) {
                    if (response.isSuccessful) {
                        Show.showLog("新闻详情网络请求成功！${response.body()!!.code}")
                        _newsInfo.postValue(response.body()!!.data)
                    }
                }

                override fun onFailure(call: Call<NewsC>, t: Throwable) {
                    Show.showLog("新闻详情网络请求失败！${t.message}")
                }
            })
    }

    fun uploadLocation(lon:String,lat:String,uid:String,time:String){
        HomeRetrofitUtil.api.uploadLocation(lon,lat,uid,time)
            .enqueue(object : Callback<Location> {
                override fun onResponse(
                    call: Call<Location>,
                    response: Response<Location>
                ) {
                    if (response.isSuccessful) {
                        Show.showLog("定位上传请求成功！")
                    }
                }

                override fun onFailure(call: Call<Location>, t: Throwable) {
                    Show.showLog("定位上传请求失败！${t.message}")
                }
            })
    }
}