package com.sychen.login.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.network.BaseResult
import com.sychen.basic.network.BaseViewModel
import com.sychen.basic.util.Show
import com.sychen.login.database.model.User
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.UserInfo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class RegisterViewModel : ViewModel() {
    private val _registerInfo by lazy {
        MutableLiveData<UserInfo>()
    }

    fun registerUser(userInfo: String): LiveData<UserInfo> {
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfo)
        viewModelScope.launch {
            val result = LoginRetrofitUtil.api.register(body)
            when (result.code) {
                200 -> {
                    _registerInfo.postValue(result.data)
                }
                201 -> {
                    Show.showToastShort("注册失败！！！")
                }
            }
        }
        return _registerInfo
    }
}