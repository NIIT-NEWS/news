package com.sychen.login.ui.welcome

import androidx.lifecycle.ViewModel
import com.sychen.basic.Utils.Show
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.VerifyToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GreetViewModel : ViewModel() {

    fun verifyToken(token:String){
        LoginRetrofitUtil.api.verifyToken(token)
            .enqueue(object : Callback<VerifyToken> {
                override fun onResponse(
                    call: Call<VerifyToken>,
                    response: Response<VerifyToken>
                ) {
                    if (response.isSuccessful) {
                    }
                }

                override fun onFailure(call: Call<VerifyToken>, t: Throwable) {
                    Show.showLog("用户登录网络请求失败！${t.message}")
                }
            })
    }
}