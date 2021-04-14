package com.sychen.login.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.Utils.Show
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.Login
import com.sychen.login.network.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private var _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo
    fun getUserInfo(id: String) {
        LoginRetrofitUtil.api.getUserById(id)
            .enqueue(object : Callback<UserInfo> {
                override fun onResponse(
                    call: Call<UserInfo>,
                    response: Response<UserInfo>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.code == 200) {
                            Show.showLog("用户登录网络请求成功！${response.body()!!}")
                            _userInfo.postValue(response.body()!!)
                        } else {
                            Show.showLog("用户登录失败")
                        }
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Show.showLog("用户登录网络请求失败！${t.message}")
                }
            })
    }
}