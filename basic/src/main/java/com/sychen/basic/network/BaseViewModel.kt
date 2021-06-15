package com.sychen.basic.network
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.sychen.basic.MyApplication.Companion.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

open class BaseViewModel : ViewModel(), LifecycleObserver {
    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            Log.e(TAG, "launchUI: $this", )
            withTimeout(15 * 1000) {
                block()
            }
            //异常处理，暂不处理
        } catch (e: Exception) {
            Log.e(TAG, "launchUI: $e", )
        }
    }


}