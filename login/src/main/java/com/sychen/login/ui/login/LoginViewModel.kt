package com.sychen.login.ui.login

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.MyApplication.Companion.showToastShort
import com.sychen.basic.network.BaseResult
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.Show
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.UserInfo
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginInfo by lazy {
        MutableLiveData<BaseResult<UserInfo>>()
    }

    fun login(account: String, password: String): LiveData<BaseResult<UserInfo>> {
        viewModelScope.launch {
            try {
                val login = LoginRetrofitUtil.api.login(account, password)
                _loginInfo.postValue(login)
            } catch (e: Exception) {
                if ("HTTP 404 Not Found" == e.message) {
                    Log.e(TAG, "login: ${e.message}")
                }
            }

        }
        return _loginInfo
    }
}