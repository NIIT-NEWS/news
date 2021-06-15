package com.sychen.login.ui.welcome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.dataStoreRead
import com.sychen.login.network.LoginRetrofitUtil
import com.sychen.login.network.model.VerifyToken
import kotlinx.coroutines.launch

enum class TokenStatus {
    Success,
    Expired,
    Failed
}

class GreetViewModel : ViewModel() {
    val status by lazy {
        MutableLiveData<TokenStatus>()
    }

    fun verifyToken() {
        viewModelScope.launch {
            val token = dataStoreRead<String>("TOKEN")
            try {
                val verifyToken = LoginRetrofitUtil.api.verifyToken(token)
                when (verifyToken.code) {
                    200 -> {
                        status.postValue(TokenStatus.Success)
                    }
                    201 -> {
                        status.postValue(TokenStatus.Expired)
                    }
                    else -> {
                        status.postValue(TokenStatus.Failed)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "verifyToken: $e" )
                status.postValue(TokenStatus.Failed)
            }

        }
    }
}