package com.sychen.home.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {
    private var _reviewInfo = MutableLiveData<List<Review.Data>>()
    val reviewInfo: LiveData<List<Review.Data>> = _reviewInfo

    fun getReviewByNid(nid: String) {
        HomeRetrofitUtil.api.getReviewByNid(nid).enqueue(
            object : Callback<Review> {
                override fun onResponse(call: Call<Review>, response: Response<Review>) {
                    if (response.isSuccessful) {
                        when (response.body()!!.code) {
                            200 -> {
                                Log.e(TAG, "新闻id$nid"+"评论获取成功")
                                _reviewInfo.postValue(response.body()!!.data)
                            }
                            201 -> {
                                Log.e(TAG, "新闻id$nid"+"评论获取失败${response.body()!!.code}")
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<Review>, t: Throwable) {
                    Log.e(TAG, "新闻id$nid"+"评论获取失败")
                }
            }
        )
    }
}