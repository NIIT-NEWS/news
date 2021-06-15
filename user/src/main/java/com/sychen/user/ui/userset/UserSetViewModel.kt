package com.sychen.user.ui.userset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.util.dataStoreRead
import com.sychen.user.network.UserRetrofitUtil
import kotlinx.coroutines.launch

class UserSetViewModel : ViewModel() {
    private val _updateAvatar by lazy {
        MutableLiveData<Int>()
    }

    fun updateAvatar(avatarUrl: String): LiveData<Int> {
        viewModelScope.launch {
            val token = dataStoreRead<String>("TOKEN").toString()
            try {
                val updateName = UserRetrofitUtil.api.updateAvatar(token, avatarUrl)
                _updateAvatar.postValue(updateName.code)
            } catch (e: Exception) {
                _updateAvatar.postValue(500)
            }
        }
        return _updateAvatar
    }
}