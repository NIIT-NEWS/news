package com.sychen.basic

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.sychen.basic.util.DataStoreUtil

class MyApplication : Application() {
    companion object {

        const val TAG = "TAG"
        private val isDebug = true
        var _context: Application? = null

        fun getContext(): Context {
            return _context!!
        }

        @SuppressLint("ShowToast")
        fun showToastShort(message: String) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
        }

        fun log(message: String) {
            Log.e(TAG, message)
        }

    }

    override fun onCreate() {
        super.onCreate()
        _context = this
        DataStoreUtil.instance.init(this)
        initARouter()
    }

    private fun initARouter() {
        if (isDebug){
            //打印日志
            ARouter.openLog()
            //启动调试模式
            ARouter.openDebug()
            ARouter.init(this)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

}