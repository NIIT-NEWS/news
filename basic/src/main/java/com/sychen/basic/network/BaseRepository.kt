package com.sychen.basic.network

import android.util.Log
import com.sychen.basic.MyApplication.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> request(call: suspend () -> BaseResult<T>): BaseResult<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            Log.e(TAG, "request: $this" )
            when (code) {
                0, 200 -> this
                201 -> ""
                403 -> ""
                404 -> ""
                500 -> ""
                else -> ""
            }
        }
    }

}