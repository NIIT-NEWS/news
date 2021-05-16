package com.sychen.user.ui.usersetname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.network.BaseResult
import com.sychen.basic.util.Show
import com.sychen.user.network.UserRetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetNameViewModel : ViewModel() {
    private var _flag = MutableLiveData<Boolean>()
    val flag: LiveData<Boolean> = _flag

    fun verifyName(name: String) {
        UserRetrofitUtil.api.verifyName(name)
            .enqueue(object : Callback<BaseResult<Any>> {
                override fun onResponse(
                    call: Call<BaseResult<Any>>,
                    response: Response<BaseResult<Any>>
                ) {
                    if (response.isSuccessful) {
                        when(response.body()!!.code){
                            202->{
                                Show.showLog("用户名存在！${response.body()!!.code}")
                                _flag.postValue(true)
                            }
                            203->{
                                Show.showLog("用户名不存在")
                                _flag.postValue(false)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResult<Any>>, t: Throwable) {
                    Show.showLog("用户名不存在！${t.message}")
                    _flag.postValue(false)
                }
            })
    }
}