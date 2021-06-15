package com.sychen.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.util.Show
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.Banner
import com.sychen.home.network.model.Location
import com.sychen.home.network.model.New
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    private var _newsInfo = MutableLiveData<List<New.Data>>()
    val newsInfo: LiveData<List<New.Data>> = _newsInfo
    private var _bannerInfo = MutableLiveData<List<Banner.Data>>()
    val bannerInfo: LiveData<List<Banner.Data>> = _bannerInfo

    init {
        getAllNews()
        getBanner()
    }

    fun getAllNews() {
        HomeRetrofitUtil.api.getAllNewsC()
            .enqueue(object : Callback<New> {
                override fun onResponse(
                    call: Call<New>,
                    response: Response<New>
                ) {
                    if (response.isSuccessful) {
                        when(response.body()!!.code){
                            200->{
                                Show.showLog("新闻详情网络请求成功！${response.body()!!.code}")
                                _newsInfo.postValue(response.body()!!.data)
                            }
                            201->{
                                Show.showLog("新闻详情网络请求失败！${response.body()!!.code}")
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<New>, t: Throwable) {
                    Show.showLog("新闻详情网络请求失败！${t.message}")
                }
            })
    }

    fun uploadLocation(lon: String, lat: String, uid: String, time: String) {
        HomeRetrofitUtil.api.uploadLocation(lon, lat, uid, time)
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

    fun getBanner() {
        HomeRetrofitUtil.api.getBanner()
            .enqueue(object : Callback<Banner> {
                override fun onResponse(
                    call: Call<Banner>,
                    response: Response<Banner>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()!!.code) {
                            200 -> {
                                Show.showLog("轮播图请求成功！")
                                _bannerInfo.postValue(response.body()!!.data)
                            }
                            else -> {
                                Show.showLog("轮播图请求失败！")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Banner>, t: Throwable) {
                    Show.showLog("轮播图请求失败！${t.message}")
                }
            })
    }
}