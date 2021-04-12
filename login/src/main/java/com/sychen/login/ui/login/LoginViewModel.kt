package com.sychen.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.Utils.Show
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private var _flag = MutableLiveData<Boolean>()
    val flag: LiveData<Boolean> = _flag
    private var _loginInfo = MutableLiveData<Login>()
    val loginInfo: LiveData<Login> = _loginInfo

    fun login(account: String, password: String): MutableLiveData<Boolean> {
        LoginRetrofitUtil.api.login(account, password)
            .enqueue(object : Callback<Login> {
                override fun onResponse(
                    call: Call<Login>,
                    response: Response<Login>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.code == 200) {
                            Show.showLog("用户登录网络请求成功！${response.body()!!}")
                            _loginInfo.postValue(response.body()!!)
                            _flag.postValue(true)
                        } else {
                            Show.showLog("用户登录失败")
                            _flag.postValue(false)
                        }
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Show.showLog("用户登录网络请求失败！${t.message}")
                    _flag.postValue(true)
                }
            })
        return _flag
    }
}