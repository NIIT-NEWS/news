package com.sychen.login.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.util.Show
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.VerifyToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GreetViewModel : ViewModel() {

    private var _flag = MutableLiveData<Boolean>()
    val flag: LiveData<Boolean> = _flag

    private var _tokenInfo = MutableLiveData<VerifyToken>()
    val tokenInfo: LiveData<VerifyToken> = _tokenInfo

    fun verifyToken(token: String) {
        LoginRetrofitUtil.api.verifyToken(token)
            .enqueue(object : Callback<VerifyToken> {
                override fun onResponse(
                    call: Call<VerifyToken>,
                    response: Response<VerifyToken>
                ) {
                    if (response.isSuccessful) {
                        when(response.body()!!.code){
                            200->{
                                Show.showLog("验证token成功！${response.body()!!}")
                                _tokenInfo.postValue(response.body()!!)
                                _flag.postValue(true)
                            }
                            201->{
                                Show.showLog("验证token成功！==== token 过期${response.body()!!}")
                                _flag.postValue(false)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<VerifyToken>, t: Throwable) {
                    Show.showLog("验证token失败！${t.message}")
                    _flag.postValue(false)
                }
            })
    }
}