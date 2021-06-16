package com.sychen.home.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.Show
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.Banner
import com.sychen.home.network.model.Location
import com.sychen.home.network.model.NiitNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    private val _newsInfo by lazy {
        MutableLiveData<NiitNews>()
    }
    private var _bannerInfo = MutableLiveData<List<Banner.Data>>()
    val bannerInfo: LiveData<List<Banner.Data>> = _bannerInfo

    init {
        getBanner()
    }

    fun getPagingData(type: Int): Flow<PagingData<NiitNews.News>> {
        return Repository.getPagingData(type).cachedIn(viewModelScope)
    }

    fun getAllNews(type: Int, pageNum: Int, pageSize: Int): LiveData<NiitNews> {
        viewModelScope.launch {
            try {
                val newsPage = HomeRetrofitUtil.api.getNewsPage(type, pageNum, pageSize)
                when (newsPage.code) {
                    200 -> {
                        _newsInfo.postValue(newsPage.data)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "getAllNews: $e")
            }

        }
        return _newsInfo
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