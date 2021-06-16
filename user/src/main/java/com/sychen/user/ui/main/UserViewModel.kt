package com.sychen.user.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.dataStoreRead
import com.sychen.user.network.UserRetrofitUtil
import com.sychen.user.network.model.UserInfo
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _userInfo by lazy {
        MutableLiveData<UserInfo>()
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): LiveData<UserInfo> {
        viewModelScope.launch {
            try {
                val userById = UserRetrofitUtil.api.getUserInfo(dataStoreRead("TOKEN"))
                _userInfo.postValue(userById.data)
            } catch (e: Exception) {
                Log.e(TAG, "getUserInfo: $e")
            }
        }
        return _userInfo
    }
}