package com.sychen.anew.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.network.MainRetrofit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NetworkService : LifecycleService() {
    val netCodeLiveData = MutableLiveData(200)
    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: NetworkService")
    }

    inner class NetBinder : Binder() {
        val service = this@NetworkService
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        lifecycleScope.launch {
            while (true) {
                delay(1_00000)
                Log.e(TAG, "onStartCommand: NetworkService")
                try {
                    val net = MainRetrofit.api.getNet()
                    if (net.code == 200) {
                        netCodeLiveData.postValue(200)
                        Log.e(TAG, "onStartCommand: 服务器连接成功")
                    }
                } catch (e: Exception) {
                    netCodeLiveData.postValue(0)
                }

            }
        }
        return NetBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: NetworkService")
    }
}