package com.sychen.home.ui.newdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.home.network.HomeRetrofitUtil
import com.sychen.home.network.model.InsertReview
import com.sychen.home.network.model.New
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewDetailsViewModel : ViewModel() {
    private var _status = MutableLiveData<Int>()
    val status: LiveData<Int> = _status

    fun uploadReview(reviewInfo: String) {
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reviewInfo)
        HomeRetrofitUtil.api.uploadReview(body).enqueue(object : Callback<InsertReview> {
            override fun onResponse(call: Call<InsertReview>, response: Response<InsertReview>) {
                when (response.body()!!.code) {
                    200 -> {
                        _status.postValue(200)
                        Log.e(TAG, "评论上传成功")
                    }
                    201 -> {
                        _status.postValue(201)
                        Log.e(TAG, "评论上传失败")
                    }
                }
            }

            override fun onFailure(call: Call<InsertReview>, t: Throwable) {
                Log.e(TAG, "评论上传失败$t")
            }
        })
    }
}