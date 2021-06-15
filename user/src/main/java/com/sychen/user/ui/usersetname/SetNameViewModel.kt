package com.sychen.user.ui.usersetname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.network.BaseResult
import com.sychen.basic.util.Show
import com.sychen.basic.util.dataStoreRead
import com.sychen.user.network.UserRetrofitUtil
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class SetNameViewModel : ViewModel() {
    private val _updateName by lazy {
        MutableLiveData<Int>()
    }

    fun updateName(name: String): LiveData<Int> {
        viewModelScope.launch {
            try {
                val token = dataStoreRead<String>("TOKEN").toString()
                val updateName = UserRetrofitUtil.api.updateName(token, name)
                _updateName.postValue(updateName.code)
            } catch (e: Exception) {
                _updateName.postValue(500)
            }
        }
        return _updateName
    }

}