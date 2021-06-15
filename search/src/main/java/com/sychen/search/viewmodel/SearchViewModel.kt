package com.sychen.search.viewmodel

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.search.network.SearchRetrofit
import com.sychen.search.network.model.News
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {
    private val _newList by lazy {
        MutableLiveData<List<News>>()
    }

    fun getNewsList(title:String):LiveData<List<News>>{
        viewModelScope.launch {
            try {
                val news = SearchRetrofit.api.getNews(title)
                _newList.postValue(news.data)
            } catch (e: Exception) {
                Log.e(TAG, "getNewsList: $e" )
            }
        }
        return _newList
    }
}