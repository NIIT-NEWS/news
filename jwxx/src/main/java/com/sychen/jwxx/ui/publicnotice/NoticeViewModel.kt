package com.sychen.jwxx.ui.publicnotice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.Show
import com.sychen.jwxx.network.JwxxRetrofitUtil
import com.sychen.jwxx.network.model.PublicData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeViewModel : ViewModel() {
    private val _noticeList by lazy {
        MutableLiveData<List<PublicData>>()
    }

    fun getAllNotice(): LiveData<List<PublicData>> {
        try {
            viewModelScope.launch {
                val allNotice = JwxxRetrofitUtil.api.getAllNotice()
                _noticeList.postValue(allNotice.data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAllNotice: $e" )
        }
        return _noticeList
    }
}